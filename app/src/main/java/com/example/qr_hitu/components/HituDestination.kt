package com.example.qr_hitu.components


interface HituDestination {
    val route: String
}

object Login : HituDestination {
    override val route = "login"
}

object  ScanProf: HituDestination {
    override val route = "scanner_login"
}

object  ScanInput: HituDestination {
    override val route = "scanner_input"
}

object  MalfList: HituDestination {
    override val route = "malfunctions_list"
}

object  MalfInfo: HituDestination {
    override val route = "malfunctions_info"
}

object  Create1: HituDestination {
    override val route = "create_qr_phase1"
}

object  Create2: HituDestination {
    override val route = "create_qr_phase2"
}

object  Create3: HituDestination {
    override val route = "create_qr_final"
}

object  ChooseQr: HituDestination {
    override val route = "choose_qr"
}

object  TransferQr: HituDestination {
    override val route = "transfer_qr"
}

object  AdminChoices: HituDestination {
    override val route = "admin_choices"
}

object  ScanAdmin: HituDestination {
    override val route = "scanner_admin"
}

object  ScannerAdminInfo: HituDestination {
    override val route = "scanner_admin_info"
}

object  ScannerAdminInfoUpdate: HituDestination {
    override val route = "scanner_admin_info_update"
}

object  SettingOptions: HituDestination {
    override val route = "settings_options"
}

object  Manual: HituDestination {
    override val route = "Manual"
}

object  Loading: HituDestination {
    override val route = "loading"
}

object  UserChoices: HituDestination {
    override val route = "user_choices"
}

object MQRLocal: HituDestination {
    override val route = "missQR_Local"
}

object RecentScanList: HituDestination {
    override val route = "recentScan_List"
}

object MissingQrList: HituDestination {
    override val route = "missingQr_List"
}

object TabScreen: HituDestination {
    override val route = "tab_screen"
}