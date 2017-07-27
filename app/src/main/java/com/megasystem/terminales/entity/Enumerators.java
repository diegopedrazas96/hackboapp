package com.megasystem.terminales.entity;


import java.io.Serializable;


public class Enumerators implements Serializable {
    public static final class SaleStatus{
        public final static long Disable=0;
        public final static long Enable=1;
        public final static long Sent=2;
    }
    public static final class ActionForm {
        public final static long Insert = 1;
        public final static long Update = 2;
    }
    public static final class Service {
        public final static int Ok = 0;
        public final static int NoConnect = 1;
        public final static int Error = 2;
        public final static int NotFound = 3;
    }

    public static final class Classifiers {
        public final static int AppType = 1;
        public final static int StatusType = 2;
        public final static int CountType = 3;
    }

    public static final class CountType {
        public final static int Manual = 6;
        public final static int Automatic = 7;
    }
    public static final class StatusService {
        public final static int Ok = 0;
        public final static int NoConnect = 1;
        public final static int Error = 2;
    }
    public static final class StatusWeb {

        public final static int Ok = 0;
        public final static int NoConnect = 1;
        public final static int Error = 2;
    }
}
