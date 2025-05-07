package sun.awt.screencast;

import sun.awt.SunToolkit;

import java.awt.Toolkit;

public class XdgDesktopPortal {
    private static final String METHOD_X11 = "x11";
    private static final String METHOD_SCREENCAST = "dbusScreencast";
    private static final String METHOD_REMOTE_DESKTOP = "dbusRemoteDesktop";

    private static final String method;
    private static final boolean isRemoteDesktop;
    private static final boolean isScreencast;

    private XdgDesktopPortal() {}

    static {
        boolean isOnWayland = false;

        if (Toolkit.getDefaultToolkit() instanceof SunToolkit sunToolkit) {
            isOnWayland = sunToolkit.isRunningOnWayland();
        }

        //TODO is X11 useless?
        method = System.getProperty(
                "awt.robot.screenshotMethod",
                isOnWayland
                        ? METHOD_REMOTE_DESKTOP
                        : METHOD_X11
        );

        isRemoteDesktop = METHOD_REMOTE_DESKTOP.equals(method);
        isScreencast = METHOD_SCREENCAST.equals(method);

        //TODO
        if (Boolean.getBoolean("awt.robot.screenshotDebug")) {
            System.out.printf(">> isRemoteDesktop %b isScreencast %b\n", isRemoteDesktop, isScreencast);
        }
        // TODO check !isRemoteDesktop && !isScreencast
    }

    public static String getMethod() {
        return method;
    }

    public static boolean isRemoteDesktop() {
        return isRemoteDesktop;
    }

    public static boolean isScreencast() {
        return isScreencast;
    }
}
