package com.cif.utils.base;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dialog.ModalityType;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Window;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.UUID;
import java.util.prefs.Preferences;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.openide.util.Lookup;

/**
 *
 * @author think
 */
public class Global {

    public static final int PARA_NUM_PER_LINE = 5;
    public static final byte File_ReadWrite = 1;
    //数据类型
    public static final byte DATA_BYTE = 1;
    public static final byte DATA_SHORT = 2;
    public static final byte DATA_INT = 3;
    public static final byte DATA_LONG = 4;
    public static final byte DATA_FLOAT = 5;
    public static final byte DATA_DOUBLE = 6;
    public static final byte DATA_STRING = 7;
    public static final byte DATA_MULTI_STRING = 8;
    public static final byte DATA_LIST = 9;
    public static final byte DATA_DATE = 10;
    public static final byte DATA_DEPTH = 11;
    public static final byte DATA_BITMAP = 12;
    public static final byte DATA_CUSTOM_COLOR = 13;
    public static final byte DATA_RESULT = 14;
    public static final byte DATA_VECTOR_SYMBOL = 15;
    public static final byte DATA_DEFINED_COLOR = 16;
    public static final byte DATA_UNSIGNED_BYTE = 20;
    public static final byte DATA_UNSIGNED_SHORT = 21;
    public static final byte DATA_UNSIGNED_INT = 22;
    public static final byte DATA_BOOLEAN = 30;
    public static final String DATA_BYTE_STRING = java.util.ResourceBundle.getBundle(
            "cif/base/Bundle").getString("Global.DATA_BYTE_STRING");
    public static final String DATA_SHORT_STRING = java.util.ResourceBundle.getBundle(
            "cif/base/Bundle").getString("Global.DATA_SHORT_STRING");
    public static final String DATA_INT_STRING = java.util.ResourceBundle.getBundle(
            "cif/base/Bundle").getString("Global.DATA_INT_STRING");
    public static final String DATA_LONG_STRING = java.util.ResourceBundle.getBundle(
            "cif/base/Bundle").getString("Global.DATA_LONG_STRING"); //NOI18N
    public static final String DATA_FLOAT_STRING = java.util.ResourceBundle.getBundle(
            "cif/base/Bundle").getString("Global.DATA_FLOAT_STRING"); //NOI18N
    public static final String DATA_DOUBLE_STRING = java.util.ResourceBundle.getBundle(
            "cif/base/Bundle").getString("Global.DATA_DOUBLE_STRING"); //NOI18N
    public static final String DATA_STRING_STRING = java.util.ResourceBundle.getBundle(
            "cif/base/Bundle").getString("Global.DATA_STRING_STRING"); //NOI18N
    public static final String DATA_MULTI_STRING_STRING = java.util.ResourceBundle.getBundle(
            "cif/base/Bundle").getString("Global.DATA_MULTI_STRING_STRING"); //NOI18N
    public static final String DATA_LIST_STRING = java.util.ResourceBundle.getBundle(
            "cif/base/Bundle").getString("Global.DATA_LIST_STRING"); //NOI18N
    public static final String DATA_DATE_STRING = java.util.ResourceBundle.getBundle(
            "cif/base/Bundle").getString("Global.DATA_DATE_STRING"); //NOI18N
    public static final String DATA_DEPTH_STRING = java.util.ResourceBundle.getBundle(
            "cif/base/Bundle").getString("Global.DATA_DEPTH_STRING"); //NOI18N
    public static final String DATA_BITMAP_STRING = java.util.ResourceBundle.getBundle(
            "cif/base/Bundle").getString("Global.DATA_BITMAP_STRING"); //NOI18N
    public static final String DATA_CUSTOM_COLOR_STRING = java.util.ResourceBundle.getBundle(
            "cif/base/Bundle").getString("Global.DATA_CUSTOM_COLOR_STRING"); //NOI18N
    public static final String DATA_RESULT_STRING = java.util.ResourceBundle.getBundle(
            "cif/base/Bundle").getString("Global.DATA_RESULT_STRING"); //NOI18N
    public static final String DATA_VECTOR_SYMBOL_STRING = java.util.ResourceBundle.getBundle(
            "cif/base/Bundle").getString("Global.DATA_VECTOR_SYMBOL_STRING"); //NOI18N
    public static final String DATA_DEFINED_COLOR_STRING = java.util.ResourceBundle.getBundle(
            "cif/base/Bundle").getString("Global.DATA_DEFINED_COLOR_STRING"); //NOI18N
    //深度单位制式
    public static final String DEPTH_METER = "meter"; //NOI18N
    public static final String DEPTH_FEET = "feet"; //NOI18N
    public static final String DEPTH_TIME = "s"; //NOI18N
    public static final double FEET2METER = 0.3048;
    //测井资料类型
    public static final byte LOGGING_CURVE1D = 1;
    public static final byte LOGGING_CURVE2D = 2;
    public static final byte LOGGING_CURVE3D = 3;
    public static final byte LOGGING_DOC = 4;
    public static final byte LOGGING_COMMON_TABLE = 5;
    public static final byte LOGGING_PARAM_TABLE = 6;
    public static final byte LOGGING_DATA_TABLE = 7;
    public static final byte LOGGING_DEPTH_REFERENCE = 8;
    public static final byte LOGGING_TDS = 20;
    public static final byte LOGGING_IMAGE_SET = 21;
    //水淹层曲线子类型
    public static final byte SUB_LOGGING_COMMON_CURVE = 11; //普通曲线
    public static final byte SUB_LOGGING_THICK_CURVE = 12; //厚度曲线
    public static final byte SUB_LOGGING_SQUARE_CURVE = 13; //方波曲线
    public static final byte SUB_LOGGING_RESULT_CURVE = 14; //解释结论
    public static final byte SUB_LOGGING_RESULT_CURVE_1 = 15; //解释结论1
    public static final byte SUB_LOGGING_LITH_CURVE = 16; //岩性曲线
    public static final byte SUB_LOGGING_DISCRETE_CURVE = 17; //离散曲线
    public static final byte SUB_LOGGING_PARA_CURVE = 18; //参数曲线
    public static final byte SUB_LOGGING_OTHER_CURVE = 19; //其它曲线
    //曲线三维数据子类型
    public static final byte LOGCURVE3D_SUBTYPE_ARRAY_DATA = 0;
    public static final byte LOGCURVE3D_SUBTYPE_BULK_DATA = 1;
    //数据缺省值
    public static final float NULL_FLOAT_VALUE = -99999;
    public static final double NULL_DOUBLE_VALUE = -99999;
    public static final byte NULL_BYTE_VALUE = (byte) -128;
    public static final short NULL_SHORT_VALUE = (short) -32767;
    public static final int NULL_INT_VALUE = -99999;
    public static final long NULL_LONG_VALUE = -99999;
    //数据越界时的缺省值
    public static final float MAGIC_FLOAT = -32767;
    //系统配置信息
    public static final String PLATFORM_NAME = "cifPlatform";
    public static final String PROJECT_MARK_FILE_NAME = "cifPlatform.xml";
    public static final String WORKSPACE_MARK_FILE_NAME = "workspace.xml";
    public static final String WELL_MARK_FILE_NAME = "wellproperties.xml";
    public static final String TASK_PANE_FILE_NAME = "taskPaneCfg.xml";
    public static final String MACRO_FILE_NAME = "macro.xml";
    public static final String RESULT_SETTING_FILE_NAME = "result.properties";
    public static final String PATH_PLATFORM_RESOURCE = "platformResource";
    public static final String PATH_PLATFORM_RESOURCE_CN = "platformResource_CN";
    public static final String PATH_PLATFORM_RESOURCE_EN = "platformResource_EN";
    public static final String PATH_PLATFORM_RESOURCE_RU = "platformResource_RU";
    public static final String PATH_APPMODULE = "appModule";
    public static final String PATH_TASK_PANE = "taskPane";
    public static final String PATH_SERVER_CONFIG = "servers";
    public static final String PATH_PROPERTIES_EX = "loggingPropertiesEX";
    public static final String PATH_JAVASCRIPT = "JavaScript";
    public static final String PATH_WELCOME_PAGE = "welcomePage";
    public static final String PATH_DEVELOPER_PAGE = "aboutPage";
    public static final String DEVELOPER_PAGE_FILE = "developerPage.htm";
    public static final String PATH_DRAW = "draw";
    public static final String PATH_TABLE = "table";
    public static final String PATH_ICONS = "icons";
    public static final String PATH_TEMP = "temp";
    public static final String PATH_CONFIG = "config";
    public static final String PATH_TABLEHEADER = "tableHeader";
    public static final String PATH_FORMULAR = "formular";
    public static final int CIFPLUS_MAX_STRING_LENGTH = 31;
    public static final String APP_DEFINATION_FILE_NAME = "appDefinition.xml";
    public static final String APP_CONFIGRATION_FILE_NAME = "appConfiguration.cfg";
    public static final String TABLE_HEADER_CONFIGFILE_NAME = "tableHeaderConfig.txt";
    public static final String WELL_PROPERTIES_FIELD_CONFIGFILE_NAME = "wellPropertiesConfig.txt";
    public static final String WELL_PROPERTIES_INIT_FILE_NAME = "wellPropertiesInitInfo.txt";
    public static final String MOUSE_SELECTION_PRECISION = "depthPrecision.cfg";
    public static final String PATH_UPDATER = "updater";
    public static final String PATH_DICT = "dict";
    public static final String PATH_OTHER_PLUG = "otherPlug";
    public static final String PATH_HELP = "help";
    public static final String PATH_MACRO = "macro";
    public static final String PATH_LOGGER = "logger";
    public static final String PATH_CONVERSION_JARS = "conversionJars";
    public static final String PATH_OUTPUT_JARS = "outputJars";
    public static final String PATH_TABLE_DRAWING_JARS = "tableDrawingJars";
    public static final String PATH_GRADIENT_CONFIG = "colorWidget";
    public static final String PATH_WORKFLOW = "workFlow";
    public static final String PATH_REGULATION = "regulation";
    public static final String PATH_RESOURCE_DIR_MARK = "platformResource.mark";
    public static final String PATH_SYSTEM = "system";
    public static final String PATH_RESERVED_TABLES = "reservedTables";
    public static final String PATH_ML_MODEL = "mlmodel";
    public static final String PATH_STAT_TABLE = "statTable";
    public static final String PATH_ENVIRONMENTCORRECTION = "environmentCorrection";
    public static final String CORRECTION_DEFINATION_FILE_NAME = "correctionDefination.xml";
    //固定字段
    public static final String FIXED_FIELD_DEPTH = "DEPTH";
    public static final String FIXED_FIELD_CONCLUSION = "CONCLUSION";
    //文件类型后缀
    public static final String CIFPLUS_SUFFIX = ".cifp";
    //错误类型
    public static final int ERROR_SUCCESS = 0;
    public static final int ERROR_FAILURE = -1;
    public static final int ERROR_NO_CIFPLUS_FILE = -2;
    public static final int ERROR_FILE_NO_EXIST = -3;
    public static final int ERROR_READ_WRITE_ERROR = -4;
    public static final int ERROR_VERSION_ERROR = -5;
    public static final int ERROR_LOGGING_EXISTED = -6;
    public static final int ERROR_LOGGING_NO_EXIST = -7;
    public static final int ERROR_WELL_EXIST = -8;
    public static final int ERROR_IS_ATTACHED = -9;
    public static final int ERROR_NO_DATA = -10;
    public static final int ERROR_ILLEGAL_FORMAT = -11;
    //辅助信息
    public static final String STRING_SEPARATOR = ":";
    public static final String WELCOME_PAGE_FILE = "welcomePage.htm";
    public static final int IF_EXISTED_OVER_WRITE = 0;
    public static final int IF_EXISTED_PROMPT = 1;
    public static final int IF_EXISTED_IGNORE = 2;
    public static final int IF_EXISTED_RENAME = 3;
    public static final int RET_CANCEL = 0;
    public static final int RET_OK = 1;
    public static final String MAIN_PROJECT_DESCRIPTION = java.util.ResourceBundle.getBundle(
            "cif/base/Bundle").getString("Global.MAIN_PROJECT_DESCRIPTION");
    public static final String DEFAULT_CATEGORY_NAME = "Default";
    public static final String DEFAULT_WORKSPACE_NAME = "workspace";
    public static final String DATE_FORMAT = "EEEE MM/dd/yyyy";
    public static final double EPS = 0.00001;//极小数，用于浮点数之间的比较
    public static final String ORA_PATH_SEPARATOR = "@@**"; //用于传递数据库路径时分隔datapath和登陆用户
    public static final String ORA_SPECIAL_SEPARATOR = "%%%"; //用于分割用户名和密码
    public static final String LICENSE_FILE_NAME = "cifplus.lic";
    @Deprecated
    public static final String DEFAULT_RESULT_TABLE_NAME = java.util.ResourceBundle.getBundle(
            "cif/base/Bundle").getString("Global.DEFAULT_RESULT_TABLE_NAME"); //解释结论
    @Deprecated
    public static final String DEFAULT_CORE_TABLE_NAME = java.util.ResourceBundle.getBundle(
            "cif/base/Bundle").getString("Global.DEFAULT_CORE_TABLE_NAME"); //取芯符号
    @Deprecated
    public static final String DEFAULT_LITH_TABLE_NAME = java.util.ResourceBundle.getBundle(
            "cif/base/Bundle").getString("Global.DEFAULT_LITH_TABLE_NAME"); //岩性剖面
    @Deprecated
    public static final String DEFAULT_MUDPROFILE_TABLE_NAME = java.util.ResourceBundle.getBundle(
            "cif/base/Bundle").getString("Global.DEFAULT_MUDPROFILE_TABLE_NAME"); //综合录井剖面
    public static final String DEFAULT_WELLSCHEMATIC_TABLE_NAME = java.util.ResourceBundle.getBundle(
            "cif/base/Bundle").getString("Global.DEFAULT_WELLSCHEMATIC_TABLE_NAME");//井身结构
    //平台资源
    public static final int INSTALLATION_RESOURCE = 1;
    public static final int USER_RESOURCE = 2;
    public static final int LOCAL_RESOURCE = 3;
    //特征值计算方法
    public static final byte CALC_MIN = 0;  //最小值
    public static final byte CALC_AVG = 1;  //平均值
    public static final byte CALC_MAX = 2;  //最大值
    public static final byte CALC_ALL = 3;  //最小值、最大值和平均值都要计算
    //用户级别
    public static final int USER_USER = 0;          //普通用户
    public static final int USER_MANAGER = 3;       //高级用户
    public static final int USER_ADMINISTRATOR = 5; //管理者
    //卡片后缀
    public static final String SUFFIX_DRAW_CARD = ".plc";
    public static final String SUFFIX_DRAW_TEMPLATE = ".plt";
    public static final String SUFFIX_PROCESSING_CARD = ".app";
    public static final String SUFFIX_PARAMETER_CARD = ".inp";
    public static final String SUFFIX_PRINT_CARD = ".prc";
    public static final String SUFFIX_CROSSPLOT_CARD = ".crc";
    public static final String SUFFIX_CROSSCHART_CARD = ".ccd";
    public static final String SUFFIX_HISTOGRAM_CARD = ".hcd";
    public static final String SUFFIX_MAP_CARD = ".mpc";
    public static final String SUFFIX_DEPTHCORRECT_CARD = ".dcc";
    public static final String SUFFIX_DICT = ".dict";
    public static final String SUFFIX_TVD_CARD = ".tvd";
    public static final String SUFFIX_RESULT_TABLE_CARD = ".rsd";
    public static final String SUFFIX_CURVE_EDIT_CARD = ".cec";
    public static final String SUFFIX_TXT_FILE = ".txt";
    public static final String SUFFIX_RESERVOIR_CARD = ".rpc";
    //文件打开类型
    public static int OPEN_OPTION = 0;
    public static int SAVE_OPTION = 1;
    ////////////////////////////////
    private static String installPath = null;
    private static String userHomePath = null;
    //当前java虚拟机启动时的语言环境参数
    public static final String PLATFORM_LOCALE_NODE_FLAG = "platform_locale_node";
    public static final String PLATFORM_LOCALE_KEY = "platform_locale_key";
    public static final String PLATFORM_RESOURCE_PATH = "platform_resource_path";
    public static final String PLATFORM_RESOURCE_PATH_KEY = "platform_resource_path_key";
    public static final int PLATFORM_CHINA_LOCALE = 0;
    public static final int PLATFORM_US_LOCALE = 1;
    public static final int PLATFORM_RU_LOCALE = 2;
    private static String platformLocale = null;
    //卡片类型列表
    private static Collection<? extends CardAssistance> cardAssistances = null;
    private static Collection<? extends MulCardAssistance> mulCardAssistances = null;
    public static final String AUTO_SET_LEFT_SCALE = "LEFT_AUTO";
    public static final String AUTO_SET_RIGHT_SCALE = "RIGHT_AUTO";
    public static final String AUTO_SET_MIN_SCALE = "MIN_AUTO";
    public static final String AUTO_SET_MAX_SCALE = "MAX_AUTO";
    // TVD
    public static final String TVD = "TVD";
    public static final String TVDSS = "TVDSS";
    public static final String TST = "TST";
    public static final String TVT = "TVT";
    public static final String X_OFFSET = "X_OFFSET";
    public static final String Y_OFFSET = "Y_OFFSET";
    // 图片曲线名称
    public static final String LOG_IMAGE_SET_NAME = "CIF_IMAGE_SET";

    /**
     * 获得平台安装路径
     *
     * @return 平台安装路径
     */
    public static String getInstallationPath() {
//        JOptionPane.showMessageDialog(null, System.getProperty("user.dir"));
        if (installPath != null) {
            return installPath;
        } else {

//            File file = new File(System.getProperty("user.home") + File.separator + ".cifPlatform.ini");
            File file;
            String os = System.getProperty("os.name").toLowerCase();
            if (os.startsWith("windows")) {
                file = new File(System.getenv("HOMEDRIVE") + System.getenv("HOMEPATH") + File.separator + ".cifPlatform.ini");
            } else {
                file = new File(System.getProperty("user.home") + File.separator + ".cifPlatform.ini");
            }

//            try {
//                if (!file.exists()) {
//                    file.createNewFile();
//                }
//                PrintWriter writer = new PrintWriter(file);
//                writer.print(System.getProperty("user.dir"));
//                writer.flush();
//                writer.close();
//            } catch (IOException ex) {
//                Exceptions.printStackTrace(ex);
//            }


//            if (!file.exists()) {
//                return "";
//            }

            BufferedReader in = null;
            try {
                in = new BufferedReader(new FileReader(file));
                installPath = in.readLine().trim();

                if (installPath == null) {
                    return "";
                }

                if (installPath.endsWith("\\")) {
                    installPath = installPath.substring(0, installPath.length() - 1);
                }
                if (installPath.endsWith("/")) {
                    installPath = installPath.substring(0, installPath.length() - 1);
                }
                return installPath;
            } catch (IOException ex) {
                return "";
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException ex) {
                        return "";
                    }
                }
            }
        }
    }

    /**
     * 获得平台jar包路径
     *
     * @return 平台jar包路径
     */
    public static String getSysJarPath() {
        return getInstallationPath() + File.separator + "ciflog" + File.separator + "modules";
    }

    /**
     * 获得当前用户目录
     *
     * @return 当前用户目录
     */
    public static String getUserHomePath() {
//        String path = System.getProperty("user.home") + File.separator + "." + PLATFORM_NAME;

        if (userHomePath != null) {
            return userHomePath;
        } else {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.startsWith("windows")) {
                userHomePath = System.getenv("HOMEDRIVE") + System.getenv("HOMEPATH") + File.separator + "." + PLATFORM_NAME;
            } else {
                userHomePath = System.getProperty("user.home") + File.separator + "." + PLATFORM_NAME;
            }

            File file = new File(userHomePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            return userHomePath;
        }
    }

    /**
     * 获得平台临时文件路径
     *
     * @return 平台临时文件路径
     */
    public static String getTempPath() {
        String tempPath = getUserHomePath() + File.separator + PATH_TEMP;
        File file = new File(tempPath);
//        file.deleteOnExit();
        if (!file.exists()) {
            file.mkdirs();
        }
        return tempPath;
    }

    public static void setCurrentPlatformResourcePath(String resoucePath) {
        Preferences preferences = Preferences.userRoot().node(PLATFORM_RESOURCE_PATH);
        preferences.put(PLATFORM_RESOURCE_PATH_KEY, resoucePath);
    }

    private static String getCurrentPlatformResourcePath() {
        Preferences preferences = Preferences.userRoot().node(PLATFORM_RESOURCE_PATH);
        return preferences.get(PLATFORM_RESOURCE_PATH_KEY, "");
    }

    public static String[] getPlatformResourcePaths() {
        String path = getInstallationPath();
        File installationDir = new File(path);
        if (!installationDir.exists()) {
            return null;
        }
        ArrayList<String> resourcePaths = new ArrayList<String>();
        File[] installationFiles = installationDir.listFiles();
        for (int i = 0; i < installationFiles.length; i++) {
            if (installationFiles[i].isDirectory()) {
                File file = new File(installationFiles[i].getAbsoluteFile() + File.separator + PATH_RESOURCE_DIR_MARK);
                if (file.exists()) {
                    resourcePaths.add(installationFiles[i].getName());
                }
            }
        }
        return resourcePaths.toArray(new String[0]);
    }

    /**
     * 获得当前语言环境下对应的资源名称
     *
     * @return 资源名称
     */
    public static String getLocaleResourceName() {
        String localeResourcePath = getCurrentPlatformResourcePath();
        if (localeResourcePath != null && !localeResourcePath.isEmpty()) {
            String path = getInstallationPath() + File.separator + localeResourcePath.trim();
            File file = new File(path);
            if (file.exists()) {
                return localeResourcePath;
            }
        }
        if (platformLocale == null) {
            Preferences preferences = Preferences.userRoot().node(PLATFORM_LOCALE_NODE_FLAG);
            int localeFlag = preferences.getInt(PLATFORM_LOCALE_KEY, PLATFORM_CHINA_LOCALE);
            switch (localeFlag) {
                case PLATFORM_CHINA_LOCALE:
                    platformLocale = PATH_PLATFORM_RESOURCE_CN;
                    break;
                case PLATFORM_US_LOCALE:
                    platformLocale = PATH_PLATFORM_RESOURCE_EN;
                    break;
                case PLATFORM_RU_LOCALE:
                    platformLocale = PATH_PLATFORM_RESOURCE_RU;
                    break;
                default:
                    platformLocale = PATH_PLATFORM_RESOURCE_CN;
                    break;
            }
        }
        return platformLocale;
//
//        Locale locale = Locale.getDefault();
//        if (locale.equals(Locale.CHINA)) {
//            return PATH_PLATFORM_RESOURCE_CN;
//        } else if (locale.equals(Locale.US)) {
//            return PATH_PLATFORM_RESOURCE_EN;
//        } else {
//            return PATH_PLATFORM_RESOURCE + "_" + locale.getLanguage().toUpperCase();
//        }
    }

    /**
     * 获得系统安装路径下的平台资源路径
     *
     * @return 安装路径下的平台资源路径
     */
    public static String getInstallationResourcePath() {
        String path = getInstallationPath() + File.separator + getLocaleResourceName();
        File file = new File(path);
        if (file.exists()) {
            return path;
        }
        return getInstallationPath() + File.separator + PATH_PLATFORM_RESOURCE;
    }

    /**
     * 获得系统安装路径下的平台资源子路径
     *
     * @param subPath 资源子路径
     * @return 安装路径下的平台资源子路径
     */
    public static String getInstallationResourcePath(String subPath) {
        return getInstallationResourcePath() + File.separator + subPath;
    }

    /**
     * 获得系统安装路径下的平台资源子路径下的文件路径
     *
     * @param subPath 资源子路径
     * @param fileName 资源文件名
     * @return 安装路径下的平台资源子路径下的文件路径
     */
    public static String getInstallationResourcePath(String subPath, String fileName) {
        return getInstallationResourcePath(subPath) + File.separator + fileName;
    }

    /**
     * 获得用户家路径下的平台资源路径
     *
     * @return 用户家路径下的平台资源路径
     */
    public static String getUserResourcePath() {
        return getUserHomePath() + File.separator + getLocaleResourceName();
    }

    /**
     * 获得用户家路径下的平台资源子路径
     *
     * @param subPath 资源子路径
     * @return 用户家路径下的平台资源子路径
     */
    public static String getUserResourcePath(String subPath) {
        String path = getUserResourcePath() + File.separator + subPath;
        File file = new File(path);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                path = "";
            }
        }
        return path;
    }

    /**
     * 获得用户家路径下的平台资源子路径下的文件路径
     *
     * @param subPath 资源子路径
     * @param fileName 资源文件名
     * @return 用户家路径下的平台资源子路径下的文件路径
     */
    public static String getUserResourcePath(String subPath, String fileName) {
        return getUserResourcePath(subPath) + File.separator + fileName;
    }

    /**
     * 获得数据类型对应的字符说明
     *
     * @param dataType 数据类型
     * @return 数据类型对应的字符说明
     */
    public static String GetDataTypeString(byte dataType) {
        String str = "";
        switch (dataType) {
            case DATA_BYTE:
                str = DATA_BYTE_STRING;
                break;
            case DATA_SHORT:
                str = DATA_SHORT_STRING;
                break;
            case DATA_INT:
                str = DATA_INT_STRING;
                break;
            case DATA_LONG:
                str = DATA_LONG_STRING;
                break;
            case DATA_FLOAT:
                str = DATA_FLOAT_STRING;
                break;
            case DATA_DOUBLE:
                str = DATA_DOUBLE_STRING;
                break;
            case DATA_STRING:
                str = DATA_STRING_STRING;
                break;
            case DATA_MULTI_STRING:
                str = DATA_MULTI_STRING_STRING;
                break;
            case DATA_LIST:
                str = DATA_LIST_STRING;
                break;
            case DATA_DATE:
                str = DATA_DATE_STRING;
                break;
            case DATA_DEPTH:
                str = DATA_DEPTH_STRING;
                break;
            case DATA_BITMAP:
                str = DATA_BITMAP_STRING;
                break;
            case DATA_CUSTOM_COLOR:
                str = DATA_CUSTOM_COLOR_STRING;
                break;
            case DATA_RESULT:
                str = DATA_RESULT_STRING;
                break;
            case DATA_VECTOR_SYMBOL:
                str = DATA_VECTOR_SYMBOL_STRING;
                break;
            case DATA_DEFINED_COLOR:
                str = DATA_DEFINED_COLOR_STRING;
                break;
        }
        return str;
    }

    /**
     * 获得数据类型
     *
     * @param dataTypeString 数据类型字符说明
     * @return 数据类型
     */
    public static byte GetDataTypeFromString(String dataTypeString) {
        byte dataType = DATA_STRING;
        if (dataTypeString.equals(DATA_BYTE_STRING)) {
            dataType = DATA_BYTE;
        } else if (dataTypeString.equals(DATA_SHORT_STRING)) {
            dataType = DATA_SHORT;
        } else if (dataTypeString.equals(DATA_INT_STRING)) {
            dataType = DATA_INT;
        } else if (dataTypeString.equals(DATA_LONG_STRING)) {
            dataType = DATA_LONG;
        } else if (dataTypeString.equals(DATA_FLOAT_STRING)) {
            dataType = DATA_FLOAT;
        } else if (dataTypeString.equals(DATA_DOUBLE_STRING)) {
            dataType = DATA_DOUBLE;
        } else if (dataTypeString.equals(DATA_STRING_STRING)) {
            dataType = DATA_STRING;
        } else if (dataTypeString.equals(DATA_MULTI_STRING_STRING)) {
            dataType = DATA_MULTI_STRING;
        } else if (dataTypeString.equals(DATA_LIST_STRING)) {
            dataType = DATA_LIST;
        } else if (dataTypeString.equals(DATA_DATE_STRING)) {
            dataType = DATA_DATE;
        } else if (dataTypeString.equals(DATA_DEPTH_STRING)) {
            dataType = DATA_DEPTH;
        } else if (dataTypeString.equals(DATA_BITMAP_STRING)) {
            dataType = DATA_BITMAP;
        } else if (dataTypeString.equals(DATA_CUSTOM_COLOR_STRING)) {
            dataType = DATA_CUSTOM_COLOR;
        } else if (dataTypeString.equals(DATA_RESULT_STRING)) {
            dataType = DATA_RESULT;
        } else if (dataTypeString.equals(DATA_VECTOR_SYMBOL_STRING)) {
            dataType = DATA_VECTOR_SYMBOL;
        } else if (dataTypeString.equals(DATA_DEFINED_COLOR_STRING)) {
            dataType = DATA_DEFINED_COLOR;
        }
        return dataType;
    }

//    /**
//     * 获得卡片类型说明
//     *
//     * @param suffix 卡片后缀
//     * @return 卡片类型说明
//     */
//    public static String getCardTypeString(String suffix) {
//        String str = "";
//        if (suffix.equalsIgnoreCase(SUFFIX_DRAW_CARD)) {
//            str = java.util.ResourceBundle.getBundle("cif/base/Bundle").getString("Global.getCardTypeString.SUFFIX_DRAW_CARD");
//        } else if (suffix.equalsIgnoreCase(SUFFIX_CROSSPLOT_CARD)) {
//            str = java.util.ResourceBundle.getBundle("cif/base/Bundle").getString("Global.getCardTypeString.SUFFIX_CROSSPLOT_CARD");
//        } else if (suffix.equalsIgnoreCase(SUFFIX_MAP_CARD)) {
//            str = java.util.ResourceBundle.getBundle("cif/base/Bundle").getString("Global.getCardTypeString.SUFFIX_MAP_CARD");
//        } else if (suffix.equalsIgnoreCase(SUFFIX_PRINT_CARD)) {
//            str = java.util.ResourceBundle.getBundle("cif/base/Bundle").getString("Global.getCardTypeString.SUFFIX_PRINT_CARD");
//        } else if (suffix.equalsIgnoreCase(SUFFIX_PARAMETER_CARD)) {
//            str = java.util.ResourceBundle.getBundle("cif/base/Bundle").getString("Global.getCardTypeString.SUFFIX_PARAMETER_CARD");
//        } else if (suffix.equalsIgnoreCase(SUFFIX_PROCESSING_CARD)) {
//            str = java.util.ResourceBundle.getBundle("cif/base/Bundle").getString("Global.getCardTypeString.SUFFIX_PROCESSING_CARD");
//        } else if (suffix.equalsIgnoreCase(SUFFIX_DEPTHCORRECT_CARD)) {
//            str = java.util.ResourceBundle.getBundle("cif/base/Bundle").getString("Global.getCardTypeString.SUFFIX_DEPTHCORRECT_CARD");
//        } else if (suffix.equalsIgnoreCase(SUFFIX_RESULT_TABLE_CARD)) {
//            str = java.util.ResourceBundle.getBundle("cif/base/Bundle").getString("Global.getCardTypeString.SUFFIX_RESULT_TABLE_CARD");
//        } else if (suffix.equalsIgnoreCase(SUFFIX_TVD_CARD)) {
//            str = java.util.ResourceBundle.getBundle("cif/base/Bundle").getString("Global.getCardTypeString.SUFFIX_TVD_CARD_CARD");
//        } else if (suffix.equalsIgnoreCase(SUFFIX_CURVE_EDIT_CARD)) {
//            str = java.util.ResourceBundle.getBundle("cif/base/Bundle").getString("Global.getCardTypeString.SUFFIX_CURVE_EDIT_CARD");
//        } else if (suffix.equalsIgnoreCase(SUFFIX_TXT_FILE)) {
//            str = java.util.ResourceBundle.getBundle("cif/base/Bundle").getString("Global.getCardTypeString.SUFFIX_TXT_FILE");
//        }
//        return str;
//    }
    /**
     * 获得测井资料类型说明
     *
     * @param loggingType 测井资料类型
     * @return 测井资料类型说明
     */
    public static String GetLoggingTypeString(byte loggingType) {
        String str = "";
        switch (loggingType) {
            case LOGGING_CURVE1D:
                str = java.util.ResourceBundle.getBundle("cif/base/Bundle").getString("Global.GetLoggingTypeString.LOGGING_CURVE1D");
                break;
            case LOGGING_CURVE2D:
                str = java.util.ResourceBundle.getBundle("cif/base/Bundle").getString("Global.GetLoggingTypeString.LOGGING_CURVE2D");
                break;
            case LOGGING_CURVE3D:
                str = java.util.ResourceBundle.getBundle("cif/base/Bundle").getString("Global.GetLoggingTypeString.LOGGING_CURVE3D");
                break;
            case LOGGING_DOC:
                str = java.util.ResourceBundle.getBundle("cif/base/Bundle").getString("Global.GetLoggingTypeString.LOGGING_DOC");
                break;
            case LOGGING_COMMON_TABLE:
                str = java.util.ResourceBundle.getBundle("cif/base/Bundle").getString("Global.GetLoggingTypeString.LOGGING_COMMON_TABLE");
                break;
            case LOGGING_PARAM_TABLE:
                str = java.util.ResourceBundle.getBundle("cif/base/Bundle").getString("Global.GetLoggingTypeString.LOGGING_PARAM_TABLE");
                break;
            case LOGGING_DATA_TABLE:
                str = java.util.ResourceBundle.getBundle("cif/base/Bundle").getString("Global.GetLoggingTypeString.LOGGING_DATA_TABLE");
                break;
            case LOGGING_DEPTH_REFERENCE:
                str = java.util.ResourceBundle.getBundle("cif/base/Bundle").getString("Global.GetLoggingTypeString.LOGGING_DEPTHINDEX_TABLE");
                break;
            case LOGGING_TDS:
                str = java.util.ResourceBundle.getBundle("cif/base/Bundle").getString("Global.GetLoggingTypeString.LOGGING_TDS");
                break;
            case LOGGING_IMAGE_SET:
                str = java.util.ResourceBundle.getBundle("cif/base/Bundle").getString("Global.GetLoggingTypeString.LOGGING_IMAGE_SET");
                break;
        }
        return str;
    }

    /**
     * 获得数据类型占用字节大小
     *
     * @param dataType 数据类型
     * @return 数据类型占用字节大小
     */
    public static byte sizeof(byte dataType) {
        byte byteSize = -1;
        switch (dataType) {
            case DATA_BYTE:
            case DATA_UNSIGNED_BYTE:
                byteSize = 1;
                break;
            case DATA_SHORT:
            case DATA_UNSIGNED_SHORT:
                byteSize = 2;
                break;
            case DATA_INT:
            case DATA_UNSIGNED_INT:
                byteSize = 4;
                break;
            case DATA_LONG:
                byteSize = 8;
                break;
            case DATA_FLOAT:
                byteSize = 4;
                break;
            case DATA_DOUBLE:
            case DATA_DEPTH:
                byteSize = 8;
                break;
            case DATA_STRING:
            case DATA_MULTI_STRING:
            case DATA_LIST:
            case DATA_DATE:
            case DATA_BITMAP:
            case DATA_CUSTOM_COLOR:
                byteSize = 2;
        }
        return byteSize;
    }

    /**
     * 按照要求截取字符串长度
     *
     * @param str 源字符串
     * @param maxCharNum 最大字符串长度
     * @return 截取后的字符串
     */
    public static String subLoggingNameString(String str, int maxCharNum) {
        String validStr = str;
        if (str == null || str.isEmpty()) {
            return validStr;
        }
        if (str.length() > maxCharNum) {
            validStr = str.substring(0, maxCharNum - 1) + "~";
        }
        return validStr;
    }

    /**
     * 将当前平台启动的语言环境写入系统注册表中
     */
    public static void writePlatformLocaleToRegistry() {
        Locale locale = Locale.getDefault();
        Preferences preferences = Preferences.userRoot().node(PLATFORM_LOCALE_NODE_FLAG);
        if (locale.equals(Locale.CHINA)) {
            preferences.putInt(PLATFORM_LOCALE_KEY, PLATFORM_CHINA_LOCALE);
        } else if (locale.equals(Locale.US)) {
            preferences.putInt(PLATFORM_LOCALE_KEY, PLATFORM_US_LOCALE);
        } else if (locale.equals(new Locale("ru", "RU"))) {//俄文
            preferences.putInt(PLATFORM_LOCALE_KEY, PLATFORM_RU_LOCALE);
        }
    }

    public static boolean isValidCifplusDataPath(File f, String child) {
        if (f == null || !f.isDirectory()) {
            return false;
        }
        return new File(f, child).exists();
    }

    public static Collection<? extends CardAssistance> getCardAssistances() {
        if (cardAssistances == null) {
            cardAssistances = Lookup.getDefault().lookupResult(
                    CardAssistance.class).allInstances();
        }
        return cardAssistances;
    }

    public static Collection<? extends MulCardAssistance> getMulCardAssistances() {
        if (mulCardAssistances == null) {
            mulCardAssistances = Lookup.getDefault().lookupResult(
                    MulCardAssistance.class).allInstances();
        }
        return mulCardAssistances;
    }

    /**
     * 返回指定组件的 Frame 父级；不会返回 null
     *
     * @param parentComponent 要检查Frame的组件
     * @return 包含组件的 Frame；如果组件为 null 或者不具有有效的 Frame 父级，则返回
     * {@link javax.swing.JOptionPane#getRootFrame}
     * @throws HeadlessException 如果 GraphicsEnvironment.isHeadless 返回 true
     * @see javax.swing.JOptionPane#getRootFrame
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public static Frame getFrameForComponent(Component parentComponent) throws HeadlessException {
        return JOptionPane.getFrameForComponent(parentComponent);
    }

    /**
     * 返回此参数对应的模式类型。
     *
     * @param modal 对话框在显示时是否阻塞用户向其他顶层窗口输入
     * @return 模式类型
     */
    public static ModalityType getModalityType(boolean modal) {
        if (modal) {
            return Dialog.DEFAULT_MODALITY_TYPE;
        } else {
            return ModalityType.MODELESS;
        }
    }

    /**
     * 返回指定组件的 Window 父级；如果指定组件未包含在 Window 内，则返回 null。
     *
     * @param component 要获取其 Window 祖先的组件
     * @return 返回指定组件的第一个 Window 祖先；如果指定组件未包含在 Window 内，则返回 null。
     */
    public static Window getWindowAncestor(Component component) {
        if (component == null) {
            return null;
        }
        if (component instanceof Window) {
            return (Window) component;
        }
        return SwingUtilities.getWindowAncestor(component);
    }

    public static String getTempFileName() {
        UUID uuid = UUID.randomUUID();
        return getTempPath() + File.separator + uuid.toString();
    }

    /*
    数据拷贝、滤波、曲线平差等三个模块是对话框模式，必须单独指定ID编码，才能找到对应帮助文档
    */
    public final static String HELP_ID_DDCOPY = "HELP_ID_DDCOPY";
    public final static String HELP_ID_FILTER = "HELP_ID_FILTER";
    public final static String HELP_ID_MAGT_SHIFT = "HELP_ID_MAGT_SHIFT";

    public static void showHelp(String helpID) {
        String helpMapPath = Global.getInstallationResourcePath(Global.PATH_CONFIG) + File.separator;
        String helpDirPath = Global.getInstallationResourcePath(Global.PATH_HELP) + File.separator;
        String helpMapFile = helpMapPath + "CIFLog-help.map";
        String helpFilePath = null;
        try {
            String encoding = "GBK";
            File file = new File(helpMapFile);
            if (file.isFile() && file.exists()) {     //判断文件是否存在
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);        //考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    String[] strs = lineTxt.split("@");
                    if (strs.length != 2) {
                        continue;
                    }
                    if (strs[0].trim().equalsIgnoreCase(helpID)) {
                        helpFilePath = helpDirPath + strs[1].trim();
                        break;
                    }
                }
                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception ex) {
            System.out.println("读取文件内容出错");
            ex.printStackTrace();
        }

        if (helpFilePath != null) {
            try {
                Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL " + helpFilePath);
            } catch (IOException ex) {
                // TODO Auto-generated catch block
                ex.printStackTrace();
            }
        }
    }
}
