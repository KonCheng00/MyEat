package com.alijiujiu.koncheng.valueChangeListener;

import java.util.EventListener;

public interface ValueChangeListener extends EventListener {
	public abstract void onBDLocationChanged(ValueChangeEvent event);

	public abstract void onPoiResultChanged(ValueChangeEvent event);

	public abstract void onPoiDetailResultChanged(ValueChangeEvent event);
}
