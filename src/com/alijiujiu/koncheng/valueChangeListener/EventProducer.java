package com.alijiujiu.koncheng.valueChangeListener;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;

public class EventProducer {
	ListenerRegister register = new ListenerRegister();
	private BDLocation location;
	private PoiResult result;
	private PoiDetailResult detailResult;

	public BDLocation getLocation() {
		return location;
	}

	public PoiResult getResult() {
		return result;
	}

	public PoiDetailResult getDetailResult() {
		return detailResult;
	}

	public void setLocation(BDLocation newLocation) {
		if (location != newLocation) {
			location = newLocation;
			ValueChangeEvent event = new ValueChangeEvent(this, location);
			fireLocationChangeEvent(event);
		}
	}

	public void setResult(PoiResult newResult) {
		if (result != newResult) {
			result = newResult;
			ValueChangeEvent event = new ValueChangeEvent(this, result);
			fireResultChangeEvent(event);
		}
	}

	public void setDetailResult(PoiDetailResult newDetailResult) {
		if (detailResult != newDetailResult) {
			detailResult = newDetailResult;
			ValueChangeEvent event = new ValueChangeEvent(this, detailResult);
			fireDetailResultChangeEvent(event);
		}
	}

	public void addListener(ValueChangeListener a) {
		register.addListener(a);
	}

	public void removeListener(ValueChangeListener a) {
		register.removeListener(a);
	}

	public void fireLocationChangeEvent(ValueChangeEvent event) {
		register.fireLocationChangeEvent(event);
	}

	private void fireResultChangeEvent(ValueChangeEvent event) {
		register.fireResultChangeEvent(event);
	}

	private void fireDetailResultChangeEvent(ValueChangeEvent event) {
		// TODO Auto-generated method stub
		register.fireDetailResultChangeEvent(event);
	}
}
