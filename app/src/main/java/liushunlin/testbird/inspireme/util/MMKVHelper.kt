package liushunlin.testbird.inspireme.util


object MMKVHelper:MMKVOwner(mapId = "fileCommander") {
    var isFirstLaunch by mmkvBoolean(default = true)
    var isFirstLaunchTime by mmkvLong(default = System.currentTimeMillis())

}