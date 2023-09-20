package liushunlin.testbird.inspireme.util


object RepositoryUtils:MMKVOwner(mapId = "fileCommander") {
    var isFirstLaunch by mmkvBoolean(default = true)
    var isFirstLaunchTime by mmkvLong(default = System.currentTimeMillis())

}