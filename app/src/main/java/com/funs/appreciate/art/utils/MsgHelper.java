package com.funs.appreciate.art.utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public final class MsgHelper {
	
	private static Handler mainHandler = null;
	
	/**
	 * 设置MainActivity的handler，用于向MainActivity发送消息
	 * @param handler
	 * 		MainActivity的handler变量
	 */
	public static void setMainHandler(Handler handler){
		mainHandler = handler;
	}
	
	/**
	 * 获取MainActivity的handler，用于向MainActivity发送消息
	 * @return
	 * 		MainActivity的handler变量
	 */
	public static Handler getMainHandler(){
		return mainHandler;
	}
	
	
	/**
	 * 向指定的handler发送消息，传入what
	 * @param handler
	 * @param what
	 */
	public static void sendMessage(Handler handler, int what){
		
		if(handler == null)
			handler = getMainHandler();
		
		if(handler != null){
			handler.sendEmptyMessage(what);
		}
	}
	
	/**
	 * 向指定的handler发送消息，传入what,arg1
	 * @param handler
	 * @param what
	 * @param arg1
	 */
	public static void sendMessage(Handler handler, int what, int arg1){
		
		if(handler == null)
			handler = getMainHandler();
		
		if(handler != null){
			Message msg = Message.obtain(handler);
			msg.what = what;
			msg.arg1 = arg1;
			handler.sendMessage(msg);
		}
	}
	
	/**
	 * 向指定的handler发送消息，传入what,obj
	 * @param handler
	 * @param what
	 * @param obj
	 */
	public static void sendMessage(Handler handler, int what, Object obj){
		
		if(handler == null)
			handler = getMainHandler();
		
		if(handler != null){
			Message msg = Message.obtain(handler);
			msg.what = what;
			msg.obj = obj;
			handler.sendMessage(msg);
		}
	}
	
	/**
	 * 向指定的handler发送消息，传入what,arg1,obj
	 * @param handler
	 * @param what
	 * @param arg1
	 * @param obj
	 */
	public static void sendMessage(Handler handler, int what, int arg1, Object obj){
		
		if(handler == null)
			handler = getMainHandler();
		
		if(handler != null){
			Message msg = Message.obtain(handler);
			msg.what = what;
			msg.arg1 = arg1;
			msg.obj = obj;
			handler.sendMessage(msg);
		}
	}
	
	/**
	 * 向指定的handler发送消息，传入what,arg1,arg2
	 * @param handler
	 * @param what
	 * @param arg1
	 * @param arg2
	 */
	public static void sendMessage(Handler handler, int what, int arg1, int arg2){
		
		if(handler == null)
			handler = getMainHandler();
		
		if(handler != null){
			Message msg = Message.obtain(handler);
			msg.what = what;
			msg.arg1 = arg1;
			msg.arg2 = arg2;
			handler.sendMessage(msg);
		}
	}
	
	/**
	 * 向指定的handler发送消息，传入what,arg1,arg2,obj
	 * @param handler
	 * @param what
	 * @param arg1
	 * @param arg2
	 * @param obj
	 */
	public static void sendMessage(Handler handler, int what, int arg1, int arg2, Object obj){
		
		if(handler == null)
			handler = getMainHandler();
		
		if(handler != null){
			Message msg = Message.obtain(handler);
			msg.what = what;
			msg.arg1 = arg1;
			msg.arg2 = arg2;
			msg.obj = obj;
			handler.sendMessage(msg);
		}
	}
	
	/**
	 * 向指定的handler发送延迟消息，传入what
	 * @param handler
	 * @param what
	 * @param delayMillis
	 */
	public static void sendMessageDelayed(Handler handler, int what, long delayMillis){
		
		if(handler == null)
			handler = getMainHandler();
		
		if(handler != null){
			handler.sendEmptyMessageDelayed(what, delayMillis);
		}
	}
	
	/**
	 * 向指定的handler发送延迟消息，传入what,arg1
	 * @param handler
	 * @param what
	 * @param arg1
	 * @param delayMillis
	 */
	public static void sendMessageDelayed(Handler handler, int what, int arg1, long delayMillis){
		
		if(handler == null)
			handler = getMainHandler();
		
		if(handler != null){
			Message msg = Message.obtain(handler);
			msg.what = what;
			msg.arg1 = arg1;
			handler.sendMessageDelayed(msg, delayMillis);
		}
	}
	
	/**
	 * 向指定的handler发送延迟消息，传入what,obj
	 * @param handler
	 * @param what
	 * @param obj
	 * @param delayMillis
	 */
	public static void sendMessageDelayed(Handler handler, int what, Object obj, long delayMillis){
		
		if(handler == null)
			handler = getMainHandler();
		
		if(handler != null){
			Message msg = Message.obtain(handler);
			msg.what = what;
			msg.obj = obj;
			handler.sendMessageDelayed(msg, delayMillis);
		}
	}
	
	/**
	 * 向指定的handler发送延迟消息，传入what,arg1,obj
	 * @param handler
	 * @param what
	 * @param arg1
	 * @param obj
	 * @param delayMillis
	 */
	public static void sendMessageDelayed(Handler handler, int what, int arg1, Object obj, long delayMillis){
		
		if(handler == null)
			handler = getMainHandler();
		
		if(handler != null){
			Message msg = Message.obtain(handler);
			msg.what = what;
			msg.arg1 = arg1;
			msg.obj = obj;
			handler.sendMessageDelayed(msg, delayMillis);
		}
	}
	
	/**
	 * 向指定的handler发送延迟消息，传入what,arg1,arg2
	 * @param handler
	 * @param what
	 * @param arg1
	 * @param arg2
	 * @param delayMillis
	 */
	public static void sendMessageDelayed(Handler handler, int what, int arg1, int arg2, long delayMillis){
		
		if(handler == null)
			handler = getMainHandler();
		
		if(handler != null){
			Message msg = Message.obtain(handler);
			msg.what = what;
			msg.arg1 = arg1;
			msg.arg2 = arg2;
			handler.sendMessageDelayed(msg, delayMillis);
		}
	}
	
	/**
	 * 向指定的handler发送延迟消息，传入what,arg1,arg2,obj
	 * @param handler
	 * @param what
	 * @param arg1
	 * @param arg2
	 * @param obj
	 * @param delayMillis
	 */
	public static void sendMessageDelayed(Handler handler, int what, int arg1, int arg2, Object obj, long delayMillis){
		
		if(handler == null)
			handler = getMainHandler();
		
		if(handler != null){
			Message msg = Message.obtain(handler);
			msg.what = what;
			msg.arg1 = arg1;
			msg.arg2 = arg2;
			msg.obj = obj;
			handler.sendMessageDelayed(msg, delayMillis);
		}
	}



	public static Bundle putBundle(Object... args){
		Bundle data = new Bundle();
		if(args != null && args.length >= 2){
			for(int i=0,count=args.length;i<count;i+=2){
				Object okey = args[i];
				Object ovalue = args[i+1];
				if(okey == null || ovalue == null)
					continue;
				if(okey instanceof String){
					String key = (String)okey;
					if (ovalue instanceof String)
						data.putString(key, (String)ovalue);
					else if (ovalue instanceof Integer)
						data.putInt(key, (Integer)ovalue);
					else if (ovalue instanceof Boolean)
						data.putBoolean(key, (Boolean)ovalue);
					else if (ovalue instanceof Long)
						data.putLong(key, (Long)ovalue);
				}
			}
		}
		return data;
	}
	
	/**
	 * 向指定的handler发送消息，传入what,args
	 * @param handler
	 * @param what
	 * @param pair 键对值，按String,Object,String,Object排列，比如String,boolean,String,int
	 */
	public static void sendMessageWithBundle(Handler handler, int what, Object... pair){
		
		if(handler == null)
			handler = getMainHandler();
		
		if(handler != null){
			Message msg = Message.obtain(handler);
			msg.what = what;
			msg.setData(putBundle(pair));
			handler.sendMessage(msg);
		}
	}

	/**
	 * 向指定的handler发送消息，传入what,arg1, args
	 * @param handler
	 * @param what
	 * @param arg1
	 * @param pair 键对值
	 */
	public static void sendMessageWithBundle1(Handler handler, int what, int arg1, Object... pair){
		
		if(handler == null)
			handler = getMainHandler();
		
		if(handler != null){
			Message msg = Message.obtain(handler);
			msg.what = what;
			msg.arg1 = arg1;
			msg.setData(putBundle(pair));
			handler.sendMessage(msg);
		}
	}
	
	/**
	 * 向指定的handler发送消息，传入what,arg1, args
	 * @param handler
	 * @param what
	 * @param arg1
	 * @param arg2
	 * @param pair 键对值
	 */
	public static void sendMessageWithBundle12(Handler handler, int what, int arg1, int arg2, Object... pair){
		
		if(handler == null)
			handler = getMainHandler();
		
		if(handler != null){
			Message msg = Message.obtain(handler);
			msg.what = what;
			msg.arg1 = arg1;
			msg.arg2 = arg2;
			msg.setData(putBundle(pair));
			handler.sendMessage(msg);
		}
	}
	
	/**
	 * 向指定的handler发送延迟消息，传入what,args
	 * @param handler
	 * @param what
	 * @param pair 键对值，按String,Object,String,Object排列，比如String,boolean,String,int
	 */
	public static void sendMessageDelayedWithBundle(Handler handler, int what, long delayMillis, Object... pair){
		
		if(handler == null)
			handler = getMainHandler();
		
		if(handler != null){
			Message msg = Message.obtain(handler);
			msg.what = what;
			msg.setData(putBundle(pair));
			handler.sendMessageDelayed(msg, delayMillis);
		}
	}

	/**
	 * 向指定的handler发送延迟消息，传入what,args
	 * @param handler
	 * @param what
	 * @param pair 键对值，按String,Object,String,Object排列，比如String,boolean,String,int
	 */
	public static void sendMessageDelayedWithBundle1(Handler handler, int what,int arg1, long delayMillis, Object... pair){
		
		if(handler == null)
			handler = getMainHandler();
		
		if(handler != null){
			Message msg = Message.obtain(handler);
			msg.what = what;
			msg.arg1 = arg1;
			msg.setData(putBundle(pair));
			handler.sendMessageDelayed(msg, delayMillis);
		}
	}
	

	public static void sendMessageWithData(Handler handler, int what, Bundle data){
		
		if(handler == null)
			handler = getMainHandler();
		
		if(handler != null){
			Message msg = Message.obtain(handler);
			msg.what = what;
			msg.setData(data);
			handler.sendMessage(msg);
		}
	}
	
	public static void sendMessageWithData(Handler handler, int what, int arg1, Bundle data){
		
		if(handler == null)
			handler = getMainHandler();
		
		if(handler != null){
			Message msg = Message.obtain(handler);
			msg.what = what;
			msg.arg1 = arg1;
			msg.setData(data);
			handler.sendMessage(msg);
		}
	}
	
	public static void sendMessageWithData(Handler handler, int what, int arg1, int arg2, Bundle data){
		
		if(handler == null)
			handler = getMainHandler();
		
		if(handler != null){
			Message msg = Message.obtain(handler);
			msg.what = what;
			msg.arg1 = arg1;
			msg.arg2 = arg2;
			msg.setData(data);
			handler.sendMessage(msg);
		}
	}
	
	public static void sendMessageWithData(Handler handler, int what, Object obj, Bundle data){
		
		if(handler == null)
			handler = getMainHandler();
		
		if(handler != null){
			Message msg = Message.obtain(handler);
			msg.what = what;
			msg.obj = obj;
			msg.setData(data);
			handler.sendMessage(msg);
		}
	}
	
	public static void sendMessageWithData(Handler handler, int what, int arg1, Object obj, Bundle data){
		
		if(handler == null)
			handler = getMainHandler();
		
		if(handler != null){
			Message msg = Message.obtain(handler);
			msg.what = what;
			msg.arg1 = arg1;
			msg.obj = obj;
			msg.setData(data);
			handler.sendMessage(msg);
		}
	}
	
	public static void sendMessageWithData(Handler handler, int what, int arg1, int arg2, Object obj, Bundle data){
		
		if(handler == null)
			handler = getMainHandler();
		
		if(handler != null){
			Message msg = Message.obtain(handler);
			msg.what = what;
			msg.arg1 = arg1;
			msg.arg2 = arg2;
			msg.obj = obj;
			msg.setData(data);
			handler.sendMessage(msg);
		}
	}
}
