package com.dailyquest.utils

class Constants {
    companion object {
        const val ORANG_TUA = "Orang Tua"
        const val ANAK = "Anak"
        const val ROLE = "role"
        const val PARENT_UID = "parent uid"

        const val EMAIL_PATTERN =
            "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:" +
                    "[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\" +
                    "[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?" +
                    "\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)" +
                    "\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:" +
                    "[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09" +
                    "\\x0b\\x0c\\x0e-\\x7f])+)\\])"

        const val DATABASE_USER = "User"

        const val SHAREDPREFERENCES = "SharedPreference Daily Quest"
        const val PREF_PARENT_UID = "pref parent uid"
        const val PREF_ROLE = "pref role"

        const val CAMERA_REQ_CODE = 9001

        const val SOURCE_ACTIVITY = "source activity"

        const val BARCODE_BASE_URL = "http://api.qrserver.com/v1/create-qr-code/?size=300x300&data="
    }
}