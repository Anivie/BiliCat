@file:Suppress("SqlNoDataSourceInspection")

package ink.bluecloud.service.clientservice.database

import ink.bluecloud.service.ClientService
import org.koin.core.annotation.Factory
import org.ktorm.database.Database

@Factory
class ConnectToDataBase: ClientService() {

    suspend fun connect(userName: String,password: String) = io {
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