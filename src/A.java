import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;
import javax.microedition.midlet.MIDlet;

public class A extends MIDlet implements CommandListener {
	
	static Font smallplainfont = Font.getFont(0, 0, Font.SIZE_SMALL);
	static Font smallboldfont = Font.getFont(0, Font.STYLE_BOLD, Font.SIZE_SMALL);

	private boolean started;
	private Form form;

	protected void destroyApp(boolean unconditional) {}

	protected void pauseApp() {}

	protected void startApp() {
		if (started) return;
		started = true;
		
		Display display = Display.getDisplay(this);
		display.setCurrent(new Form("Loading"));
		
		form = new Form("api tester v" + getAppProperty("MIDlet-Version"));
		form.addCommand(new Command("Exit", Command.EXIT, 0));
		form.setCommandListener(this);
		
		form.append("microedition.platform: " + System.getProperty("microedition.platform") + "\n");
		
		form.append("JVM:\n");
		{
			String s;
			StringBuffer sb = new StringBuffer();
			
			if ((s = System.getProperty("java.vm.name")) != null) {
				sb.append(s).append(", ").append(System.getProperty("java.vm.vendor"));
				if ((s = System.getProperty("java.vm.version")) != null) {
					sb.append('\n').append(s);
				}
				if ((s = System.getProperty("java.vm.specification.name")) != null) {
					sb.append('\n').append(s);
				}
			} else if ((s = System.getProperty("com.ibm.oti.configuration")) != null) {
				sb.append("J9 VM, IBM (").append(s).append(')');
				if ((s = System.getProperty("java.fullversion")) != null) {
					sb.append("\n\n").append(s);
				}
			} else if ((s = System.getProperty("java.fullversion")) != null) {
				sb.append(s);
			} else if ((s = System.getProperty("com.oracle.jwc.version")) != null) {
				sb.append("OJWC v").append(s).append(", Oracle");
			} else if (checkClass("com.sun.cldchi.io.ConsoleOutputStream")
					|| checkClass("com.sun.cldchi.jvm.JVM")) {
				sb.append("CLDC Hotspot Implementation, Sun");
			} else if (checkClass("com.sun.midp.io.InternalConnector")
					|| checkClass("com.sun.midp.io.ConnectionBaseAdapter")
					|| checkClass("com.sun.midp.Main")) {
				sb.append("KVM, Sun (MIDP)");
			} else if (checkClass("com.sun.cldc.util.j2me.CalendarImpl")
					|| checkClass("com.sun.cldc.i18n.Helper")
					|| checkClass("com.sun.cldc.io.ConsoleOutputStream")
					|| checkClass("com.sun.cldc.i18n.uclc.DefaultCaseConverter")) {
				sb.append("KVM, Sun (CLDC)");		    
			} else if (checkClass("com.jblend.util.SortedVector")
					|| checkClass("com.jblend.tck.socket2http.Protocol")
					|| checkClass("com.jblend.io.j2me.resource.Protocol")
					|| checkClass("com.jblend.security.midp20.SecurityManagerImpl")
					|| checkClass("com.jblend.security.midp20.UserConfirmDialogImpl")
					|| checkClass("jp.co.aplix.cldc.io.MIDPURLChecker")
					|| checkClass("jp.co.aplix.cldc.io.j2me.http.HttpConnectionImpl")) {
				sb.append("JBlend, Aplix");
			} else if (checkClass("com.jbed.io.CharConvUTF8")
					|| checkClass("com.jbed.runtime.MemSupport")
					|| checkClass("com.jbed.midp.lcdui.GameCanvasPeer")
					|| checkClass("com.jbed.microedition.media.CoreManager")
					|| checkClass("com.jbed.runtime.Mem")
					|| checkClass("com.jbed.midp.lcdui.GameCanvas")
					|| checkClass("com.jbed.microedition.media.Core")) {
				sb.append("Jbed, Esmertec/Myriad Group");
			} else if (checkClass("MahoTrans.IJavaObject")) {
				sb.append("MahoTrans");
			} else {
				sb.append("Unknown");
			}
			if ((s = System.getProperty("java.version")) != null) {
				sb.append("\nJava ").append(s).append(", ").append(System.getProperty("java.vendor"));
			}
			if ((s = System.getProperty("os.name")) != null) {
				sb.append('\n').append(s).append(' ').append(System.getProperty("os.version")).append(' ').append(System.getProperty("os.arch"));
			}
			StringItem i = new StringItem("", sb.append('\n').toString());
			i.setFont(smallplainfont);
			form.append(i);
		}
		
		form.append("API:\n");
		
		api("Configuration", new String[] {
				"JDK 1.8", "java.util.Base64",
				"JDK 1.7", "java.util.Objects",
				"JDK 1.6", "java.util.Deque",
				"JDK 1.5", "java.lang.Enum",
				"JDK 1.4", "java.util.regex.Pattern",
				"JDK 1.3", "java.lang.Terminator",
				"JDK 1.2", "java.lang.Compiler",
				"JDK 1.0", "java.util.Observer",
				"CDC 1.1", "java.lang.CharSequence",
				"CDC 1.0", "java.lang.SecurityManager",
				"CLDC 1.1.1", "java.security.Permission",
				"CLDC 1.1", "java.lang.Float",
				"CLDC 1.0"
		});
		api("MIDP", new String[] {
				"3.0", "javax.microedition.lcdui.TabbedPane",
				"2.1", ":microedition.profiles=2.1",
				"2.0", "javax.microedition.lcdui.Spacer",
				"1.0"
		});
		api("JSR-75 FileConnection", "javax.microedition.io.file.FileConnection");
		api("JSR-75 PIM", "javax.microedition.pim.PIM");
		api("JSR-82 Bluetooth", "javax.bluetooth.LocalDevice");
		api("JSR-82 OBEX", "javax.obex.HeaderSet");
		api("JSR-120 WMA", "javax.wireless.messaging.Message");
		api("JSR-135 MMAPI", new String[] {
				"", ":microedition.media.version",
				"YES", "javax.microedition.media.Manager"
		});
		api("JSR-172 RPC", "javax.microedition.xml.rpc.Type");
		api("JSR-172 XML Parser", "javax.xml.parsers.SAXParser");
		api("JSR-177 ADPU", "javax.microedition.apdu.ADPUConnection");
		api("JSR-177 Crypto", "javax.crypto.Cipher");
		api("JSR-177 JCRMI", "javax.microedition.jcrmi.JavaCardRMIConnection");
		api("JSR-177 PKI", "javax.microedition.pki.Certificate");
		api("JSR-179 Location", "javax.microedition.location.Location");
		api("JSR-180 SIP", new String[] {
				"", ":microedition.sip.version",
				"YES", "javax.microedition.sip.SipConnection"
		});
		api("JSR-184 M3G", new String[] {
				"", ":microedition.location.version",
				"YES", "javax.microedition.m3g.Node"
		});
		api("JSR-185 JTWI", new String[] {
				"", ":microedition.jtwi.version"
		});
		api("JSR-205 WMA 2.0", "javax.wireless.messaging.MessagePart");
		api("JSR-211 Content Handler", "javax.microedition.content.ContentHandler");
		api("JSR-226 M2G", new String[] {
				"", ":microedition.m2g.version",
				"YES", "javax.microedition.m2g.ScalableGraphics"
		});
		api("JSR-234 AMMS", new String[] {
				"", ":microedition.amms.version",
				"YES", "javax.microedition.amms.GlobalManager"
		});
		api("JSR-238 Internalization", new String[] {
				"", ":microedition.global.version",
				"YES", "javax.microedition.global.ResourceManager"
		});
		api("JSR-239 OpenGL ES", "javax.microedition.khronos.opengles.GL");
		api("JSR-253 Telephony", "javax.microedition.telephony.Service");
		api("JSR-256 Sensor", new String[] {
				"", ":microedition.sensor.version",
				"YES", "javax.microedition.sensor.SensorManager"
		});
		api("JSR-257 Contactless", new String[] {
				"", ":microedition.contactless.version",
				"YES", "javax.microedition.contactless.DiscoveryManager"
		});
		api("JSR-258 Customization", new String[] {
				"", ":microedition.theme.version",
				"YES", "javax.microedition.theme.Capabilities"
		});
		api("JSR-272 Broadcast", new String[] {
				"", ":microedition.broadcast.version",
				"YES", "javax.microedition.broadcast.BroadcastConnection"
		});
		api("JSR-280 XML", "javax.xml.XMLConstants");
//		api("JSR-281 IMS", "javax.microedition.ims.Service");
		api("eSWT", "org.eclipse.ercp.swt.mobile.MobileShell");
		api("Mascot Capsule", "com.mascotcapsule.micro3d.v3.Graphics3D");
		api("Nokia UI", new String[] {
				// S60v3.2+, s60 with nokia ui v1.6+, asha
				"", ":com.nokia.mid.ui.version",
				"1.6", "com.nokia.mid.ui.VirtualKeyboard",
				"1.4", "com.nokia.mid.ui.SoftNotification",
				// S40v6+
				"1.1c", ":com.nokia.mid.ui.customfontsize",
				"1.1b", "com.nokia.mid.ui.Clipboard",
				"1.1a", ":com.nokia.mid.ui.joystick_event",
				// other
				"YES", "com.nokia.mid.ui.DeviceControl",
				"YES", "com.nokia.mid.ui.DirectGraphics"
		});
		api("Nokia IAPInfo", "com.nokia.mid.iapinfo.IAPInfo");
		api("Nokia In-App Payment", "com.nokia.payment.NPayManager");
		api("Nokia In-App Purchase", "com.nokia.mid.payment.IAPClientPaymentManager");
		api("Nokia LocationUtil", "com.nokia.mid.location.LocationUtil");
		api("Nokia SMS", "com.nokia.mid.messaging.Message");
		api("Nokia Sound", "com.nokia.mid.sound.Sound");
		api("Project Capuchin", new String[] {
				"", ":com.sonyericsson.capuchin.version",
				"YES", "com.sonyericsson.capuchin.FlashCanvas"
		});
		api("Samsung", "com.samsung.util.AudioClip");
		api("Sprint Media", "com.sprintpcs.media.Player");
		api("Pigler", "org.pigler.api.PiglerAPI");
		// TODO other OEMs, v-appli
		api("Java RMI", "java.rmi.Remote");
		api("Java NIO", "java.nio.Buffer");

		display.setCurrent(form);
	}
	
	void api(String name, String[] map) {
		boolean found = true;
		String r = null;
		int i;
		for (i = 0; i < map.length; i += 2) {
			if (map.length == i + 1) {
				r = map[i];
				break;
			}
			String t = map[i + 1];
			if (t.startsWith("!")) {
				if (!checkClass(t)) {
					r = "NO";
					found = false;
					break;
				}
			} else if (t.startsWith(":")) {
				int j;
				String p;
				if ((j = t.indexOf('=')) != -1) {
					if ((p = System.getProperty(t.substring(1, j))) != null
						&& p.indexOf(t.substring(j + 1)) != -1) {
						if ("".equals(map[i])) r = p;
						break;
					}
				} else if ((t = System.getProperty(t.substring(1))) != null) {
					if ("".equals(map[i])) r = t;
					break;
				}
			} else {
				if (checkClass(t)) break;
			}
		}
		if (i != map.length) {
			if (r == null) r = map[i];
		} else {
			r = "NO";
			found = false;
		}
		StringItem s = new StringItem("", name + ": " + r + "\n");
		s.setFont(found ? smallboldfont : smallplainfont);
		form.append(s);
	}

	void api(String name, String cls) {
		boolean found = checkClass(cls);
		StringItem s = new StringItem("", name + ": " + (found ? "YES" : "NO") + "\n");
		s.setFont(found ? smallboldfont : smallplainfont);
		form.append(s);
	}
	
	static boolean checkClass(String s) {
		try {
			Class.forName(s);
			return true;
//		} catch (SecurityException e) {
//			return true;
		} catch (Exception e) {
			return false;
		} catch (Error e) {
			return true;
		} catch (Throwable e) {
			return false;
		}
	}

	public void commandAction(Command c, Displayable d) {
		notifyDestroyed();
	}

}
