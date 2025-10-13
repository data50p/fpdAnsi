package femtioprocent.ansi

object Version {
    fun info() : String {
	return "$projectVersion $buildHost $buildTimestamp ($buildNumber)"
    }
    const val projectGroup: String = "femtioprocent"
    const val projectName: String = "ansi"
    const val projectVersion: String = "0.0.1.0"
    const val buildTimestamp: String = "2025-10-13 11:31:56 +0200"
    const val buildHost = "mango-28.local"
    const val buildNumber = "283"
}
