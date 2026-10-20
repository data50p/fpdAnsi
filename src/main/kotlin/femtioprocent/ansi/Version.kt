package femtioprocent.ansi

object Version {
    fun info() : String {
	return "$projectVersion $buildHost $buildTimestamp ($buildNumber)"
    }
    const val projectGroup: String = "femtioprocent"
    const val projectName: String = "ansi"
    const val projectVersion: String = "0.0.1.0"
    const val buildTimestamp: String = "2026-10-20 14:56:39 +0200"
    const val buildHost = "melon-3.local"
    const val buildNumber = "1024"
}
