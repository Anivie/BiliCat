@file:Suppress("SqlNoDataSourceInspection")

package ink.bluecloud.service.database

import ink.bluecloud.service.ClientService
import ink.bluecloud.utils.onIO
import org.koin.core.annotation.Factory
import org.ktorm.database.Database

@Factory
class ConnectToDataBase: ClientService() {

    @Suppress("unused")
    suspend fun connect(userName: String, password: String) = onIO {
        Database.connect(
            url = "jdbc:h2:file:./config/database/bilifx-${userName};AUTO_SERVER=TRUE",
            driver = "org.h2.Driver",
            user = userName,
            password = password
        ).run {
//            database.set(this)
            checkTables(this)
        }
    }

    private fun checkTables(database: Database) {
        database.useConnection {
            it.prepareStatement(userinfoTables).executeQuery()
        }
    }

    @Suppress("SqlDialectInspection")
    private val userinfoTables = """
                    CREATE TABLE IF NOT EXISTS user_info(
                        name varchar(16) not null,
                        mid varchar(32) primary key not null,
                        cookie text not null,
                        fans int not null,
                        followers int not null,
                        signature int not null,
                        head varbinary(500) not null
                    )
                """.trimIndent()
}