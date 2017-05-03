package com.parking.service;


import android.os.Bundle;

import android.util.Log;
import android.widget.Toast;
import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.SaveListener;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.mapapi.model.inner.Point;
import com.baidu.mapapi.search.poi.PoiResult;
import com.bmob.demo.sms.bean.ParkinglotInfo;
import com.parking.R;

import java.util.ArrayList;
import java.util.List;

/**
 * ������ʾpoi��overly
 */
public class PoiService extends PoiManager {

    private static final int MAX_POI_SIZE = 10;

    private PoiResult mPoiResult = null;
    private int f;

    /**
     * ���캯��
     *
     * @param baiduMap �� PoiOverlay ���õ� BaiduMap ����
     */
    public PoiService(BaiduMap baiduMap, int flag) {
        super(baiduMap, flag);
    }

    /**
     * ����POI����
     *
     * @param poiResult ����POI����
     */
    public void setData(PoiResult poiResult) {
        this.mPoiResult = poiResult;
    }

    @Override
    public final List<OverlayOptions> getOverlayOptions() {
        if (mPoiResult == null || mPoiResult.getAllPoi() == null) {
            return null;
        }
        List<OverlayOptions> markerList = new ArrayList<OverlayOptions>();
        int markerSize = 0;

        List<BmobObject> poiResult = new ArrayList<BmobObject>();

        for (int i = 0; i < mPoiResult.getAllPoi().size()
                && markerSize < MAX_POI_SIZE; i++) {
            if (mPoiResult.getAllPoi().get(i).location == null) {
                continue;
            }
            markerSize++;
            Bundle bundle = new Bundle();
            bundle.putInt("index", i);
            if (flag == 0) {
                markerList.add(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromAssetWithDpi("Icon_mark"
                                + markerSize + ".png")).extraInfo(bundle)
                        .position(mPoiResult.getAllPoi().get(i).location));
            }
            if (flag == 1) {
                BitmapDescriptor centerBitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.pp);
                markerList.add(new MarkerOptions().icon(centerBitmap).extraInfo(bundle)
                        .position(mPoiResult.getAllPoi().get(i).location));
                //获取停车场信息写入数据库
                Double latitude = mPoiResult.getAllPoi().get(i).location.latitude;
                Double longitude = mPoiResult.getAllPoi().get(i).location.longitude;
                String parkinglotName = mPoiResult.getAllPoi().get(i).name;
                String address = mPoiResult.getAllPoi().get(i).address;


                ParkinglotInfo parkinglotInfo = new ParkinglotInfo();
                parkinglotInfo.setParkinglot_name(parkinglotName);
                parkinglotInfo.setLatitude(latitude);
                parkinglotInfo.setLongitude(longitude);
                parkinglotInfo.setDescription(address);
                parkinglotInfo.setTotalNum(40);
                parkinglotInfo.setPrice(i);

                poiResult.add(parkinglotInfo);
            }

        }

        new BmobBatch().insertBatch(poiResult).doBatch(new QueryListListener<BatchResult>() {
            @Override
            public void done(List<BatchResult> list, BmobException e) {
                if (e != null) {
                    Log.e("失败了好气！", e.getMessage());
                }
            }
        });
        return markerList;
    }

    /**
     * ��ȡ�� PoiOverlay �� poi����
     *
     * @return
     */
    public PoiResult getPoiResult() {
        return mPoiResult;
    }

    /**
     * ��д�˷����Ըı�Ĭ�ϵ����Ϊ
     *
     * @param i �������poi��
     *          {@link com.baidu.mapapi.search.poi.PoiResult#getAllPoi()} �е�����
     * @return
     */
    public boolean onPoiClick(int i) {
//        if (mPoiResult.getAllPoi() != null
//                && mPoiResult.getAllPoi().get(i) != null) {
//            Toast.makeText(BMapManager.getInstance().getContext(),
//                    mPoiResult.getAllPoi().get(i).name, Toast.LENGTH_LONG)
//                    .show();
//        }
        return false;
    }

    @Override
    public final boolean onMarkerClick(Marker marker) {
        if (!mOverlayList.contains(marker)) {
            return false;
        }
        if (marker.getExtraInfo() != null) {
            return onPoiClick(marker.getExtraInfo().getInt("index"));
        }
        return false;
    }

    @Override
    public boolean onPolylineClick(Polyline polyline) {
        // TODO Auto-generated method stub
        return false;
    }
}

