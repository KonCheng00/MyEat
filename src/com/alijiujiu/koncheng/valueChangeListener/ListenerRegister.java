package com.alijiujiu.koncheng.valueChangeListener;

import java.util.Vector;

public class ListenerRegister {
	private Vector<ValueChangeListener> listeners = new Vector<ValueChangeListener>();

	public synchronized void addListener(ValueChangeListener a) {
		listeners.addElement(a);
	}

	public synchronized void removeListener(ValueChangeListener a) {
		listeners.removeElement(a);
	}

	@SuppressWarnings("unchecked")
	public void fireLocationChangeEvent(ValueChangeEvent event) {
		Vector<ValueChangeListener> currentListeners = null;
		synchronized (this) {
			currentListeners = (Vector<ValueChangeListener>) listeners.clone();
		}
		for (int i = 0; i < currentListeners.size(); i++) {
			ValueChangeListener listener = (ValueChangeListener) currentListeners.elementAt(i);
			listener.onBDLocationChanged(event);
		}
	}

	@SuppressWarnings("unchecked")
	public void fireResultChangeEvent(ValueChangeEvent event) {
		Vector<ValueChangeListener> currentListeners = null;
		synchronized (this) {
			currentListeners = (Vector<ValueChangeListener>) listeners.clone();
		}
		for (int i = 0; i < currentListeners.size(); i++) {
			ValueChangeListener listener = (ValueChangeListener) currentListeners.elementAt(i);
			listener.onPoiResultChanged(event);
		}
	}

	@SuppressWarnings("unchecked")
	public void fireDetailResultChangeEvent(ValueChangeEvent event) {
		Vector<ValueChangeListener> currentListeners = null;
		synchronized (this) {
			currentListeners = (Vector<ValueChangeListener>) listeners.clone();
		}
		for (int i = 0; i < currentListeners.size(); i++) {
			ValueChangeListener listener = (ValueChangeListener) currentListeners.elementAt(i);
			listener.onPoiDetailResultChanged(event);
		}
	}
}
