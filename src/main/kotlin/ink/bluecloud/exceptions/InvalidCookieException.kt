package ink.bluecloud.exceptions

class InvalidCookieException(override val message:String= "This cookie is invalid. Please login again"):Exception(message) {
}