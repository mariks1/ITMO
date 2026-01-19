import lab2.functions.*
import lab2.Function
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.sql.DriverManager

@Testcontainers
class DockerTests {


    companion object {
        @Container
        val postgres = PostgreSQLContainer("postgres:17")

        @JvmStatic
        @BeforeAll
        fun setup(): Unit {
            postgres.start()

            val connection = DriverManager.getConnection(
                postgres.jdbcUrl,
                postgres.username,
                postgres.password
            )

            val statement = connection.createStatement()
            val resultSet = statement.executeQuery("SELECT 1")
            resultSet.next()
            val result = resultSet.getInt(1)
            Assertions.assertEquals(1, result)

            val createTableStatement = connection.createStatement()
            createTableStatement.execute("CREATE TABLE IF NOT EXISTS math (id INT PRIMARY KEY, reference_result DOUBLE PRECISION, result DOUBLE PRECISION)")

            val insertStatement =
                connection.prepareStatement("INSERT INTO math (id, reference_result, result) VALUES (?, ?, ?)")
            insertStatement.setInt(1, 1)
            insertStatement.setDouble(2, 1.011380846809258)
            insertStatement.setDouble(3, 0.0)
            insertStatement.executeUpdate()

            connection.close()
        }

        @JvmStatic
        @AfterAll
        fun teardown(): Unit {
            postgres.stop()
        }
    }


    @Test
    fun `another test using the same database`() {
        val connection = DriverManager.getConnection(
            postgres.jdbcUrl,
            postgres.username,
            postgres.password
        )
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT 2")
        resultSet.next()
        val result = resultSet.getInt(1)

        Assertions.assertEquals(2, result)
        connection.close()
    }

    @Test
    fun `should retrieve value from table by id`() {
        val connection = DriverManager.getConnection(
            postgres.jdbcUrl,
            postgres.username,
            postgres.password
        )
        val idToRetrieve = 1
        val statement = connection.prepareStatement("SELECT reference_result FROM math WHERE id = ?")
        statement.setInt(1, idToRetrieve)
        val resultSet = statement.executeQuery()
        Assertions.assertTrue(resultSet.next(), "No row found with id $idToRetrieve")

        val retrievedValue = resultSet.getDouble("reference_result")
        Assertions.assertEquals(1.011380846809258, retrievedValue)

        connection.close()
    }

    @Test
    fun `should write and retrieve user data correctly`() {
        val userId = 1
        val resultFunc = Function()
        val resultVal = resultFunc.func(2.0, 0.00001)

        writeResultToDatabase(userId, resultVal)

        val retrievedResult = retrieveResultFromDatabase(userId)

        Assertions.assertEquals(resultVal, retrievedResult,"Referenced result and calculated result must be equal")
    }


    private fun writeResultToDatabase(id: Int, x: Double) {
        val connection = DriverManager.getConnection(postgres.jdbcUrl, postgres.username, postgres.password)
        val insertStatement = connection.prepareStatement("UPDATE math SET result = ? WHERE id = ?")
        insertStatement.setDouble(1, x)
        insertStatement.setInt(2, id)
        insertStatement.executeUpdate()
        connection.close()
    }

    private fun retrieveResultFromDatabase(id: Int): Double {
        val connection = DriverManager.getConnection(postgres.jdbcUrl, postgres.username, postgres.password)
        val selectStatement = connection.prepareStatement("SELECT result FROM math WHERE id = ?")
        selectStatement.setInt(1, id)
        val resultSet = selectStatement.executeQuery()

        return if (resultSet.next()) {
            resultSet.getDouble("result")
        } else {
            throw NoSuchElementException("User with id $id not found")
        }
        connection.close()
    }
}