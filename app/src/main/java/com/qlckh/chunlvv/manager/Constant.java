package com.qlckh.chunlvv.manager;

/**
 * @author Andy
 * @date 2018/9/16 16:47
 * Desc:
 */
public interface Constant {

     String TEXT="1b 40 C9 E7 C7 F8 A3 BA CE E0 CD A9 BA FE C9 E7 C7 F8 0D 0A D3 C3 BB A7 C3 FB A3 BA C0 EE D5 BC B1 F8 0D 0A BB FD B7 D6 A3 BA B5 B1 C7 B0 BB FD B7 D6 20 20 20 20 30 20 20 20 20 20 20 20 D7 DC BB FD B7 D6 20 20 20 32 30 0D 0A D6 D8 C1 BF A3 BA B5 B1 C7 B0 CD B6 B7 C5 D6 D8 C1 BF 20 20 35 36 67 20 20 20 20 20 20 20 D7 DC D6 D8 C1 BF 20 20 20 38 30 67 0D 0A CA B1 BC E4 A3 BA 32 30 31 38 C4 EA 35 B5 E3 33 30 C8 D5 20 31 32 3A 33 30 0D 0A B9 AB CB BE A3 BA D5 E3 BD AD B4 BA C2 CC BB B7 B1 A3 BF C6 BC BC D3 D0 CF DE B9 AB CB BE 0d 0a 0d 0a 0d 0a 0d 0a 1b 69";

     String TEXT2="1B 40 C9 E7 20 20 20 C7 F8 A3 BA CE E0 CD A9 BA FE C9 E7 C7 F8 0D 0A D3 C3 BB A7 C3 FB A3 BA C0 EE B1 F8 D5 BE 0D 0A BB FD 20 20 20 B7 D6 A3 BA B5 B1 C7 B0 BB FD B7 D6 20 20 20 20 30 20 20 20 20 20 20 20 D7 DC BB FD B7 D6 20 20 20 32 30 0D 0A D6 D8 20 20 20 C1 BF A3 BA B5 B1 C7 B0 CD B6 B7 C5 D6 D8 C1 BF 20 20 35 36 67 20 20 20 20 20 20 20 D7 DC D6 D8 C1 BF 20 20 20 38 30 67 0D 0A CA B1 20 20 20 BC E4 A3 BA 32 30 31 38 C4 EA 39 D4 C2 31 38 C8 D5 0D 0A B9 AB 20 20 20 CB BE A3 BA D5 E3 BD AD B4 BA C2 CC BB B7 B1 A3 BF C6 BC BC D3 D0 CF DE B9 AB CB BE 0D 0A 0D 0A 0D 0A 0D 0A 1B 69";
    String SP_NAME="padd.qlckh.cn.tempad_preferences";
    /**
     * 四个桶发送指令
     */
    String SEND_WEIGHT="010400000008f1cc";
    /**
     * 三个桶发送指令
     */
    String SEND_WEIGHT_THREE="0104000000067008";

    int MAX_WEIGHT=120;
    /**
     * 面板节点 开门和关门
     */
    String PANEL_NODE="PANEL_NODE";
    String PANEL_RATE="PANEL_RATE";

    /**
     * 称重
     */
    String WEGHT_NODE="WEGHT_NODE";
    String WEGHT_RATE="WEGHT_RATE";

    /**
     * 扫码枪
     */
    String SCAN_NODE="SCAN_NODE";
    String SCAN_RATE="SCAN_RATE";

    /**
     * 打印机
     */
    String PRINT_NODE="PRINT_NODE";
    String PRINT_RATE="PRINT_RATE";


    /*===============================================================================================*/


    /**
     * 读取机器状态的指令
     */
    String QIAN_READ_STATUS="2000010021";
    /**
     * 清除运行结果指令
     */
    String QIAN_CLEAR="2000030023";
    /**
     * 双反馈 5秒超时
     */
    String QIAN_TURN="20000204****0032";
}
