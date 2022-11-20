package ink.bluecloud.exceptions

class CodeException(code: Int, msg: String? = null) : Exception("The Code field of JSON is $code, not the expected 0. result: $msg")