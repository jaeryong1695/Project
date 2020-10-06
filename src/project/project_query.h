


#ifdef __cplusplus
extern "C" {
#endif

#define QUERY_CREATE_TABLE_SENSOR_VALUE "CREATE TABLE IF NOT EXISTS autosensorvalue (id INT AUTO_INCREMENT PRIMARY KEY, date DATE, time TIME, human INT, ultra INT)"
#define QUERY_CREATE_TABLE_SENSOR_CHECK "CREATE TABLE IF NOT EXISTS autosensorcheck (id INT AUTO_INCREMENT PRIMARY KEY, date DATE, time TIME, human INT, ultra INT)"
#define QUERY_CREATE_TABLE_ACTURATOR_VALUE "CREATE TABLE IF NOT EXISTS autoactuoperate (id INT AUTO_INCREMENT PRIMARY KEY, date DATE, time TIME, dcmotor INT, rgbled INT, ledbar INT)"
#define QUERY_CREATE_TABLE_ACTURATOR_CHECK "CREATE TABLE IF NOT EXISTS autoactucheck (id INT AUTO_INCREMENT PRIMARY KEY, date DATE, time TIME, dcmotor INT, rgbled INT, ledbar INT)"
#define QUERY_CREATE_TABLE_SETTING "CREATE TABLE IF NOT EXISTS autosetting (id INT AUTO_INCREMENT PRIMARY KEY, hour INT, min INT, period INT)"

#define QUERY_SELECT_SENSOR_DATA "SELECT date, time, human, ultra FROM autosensorvalue WHERE time >= DATE_ADD(NOW(), INTERVAL-1 HOUR)"
#define QUERY_INSERT_SENSOR_DATA "INSERT INTO  autosensorvalue values (null ,now(), now(), %d,%d)"
#define QUERY_INSERT_SENSOR_CHECK "INSERT into autosensorcheck values (null, now(), now(), %d,%d)"
#define QUERY_INSERT_ACTUATOR_VALUE "INSERT into autoactuoperate values (null, now(), now(), %d, %d, %d)"
#define QUERY_INSERT_ACTUATOR_CHECK "INSERT into autoactucheck values (null, now(), now(), %d, %d, %d)"

#define QUERY_INSERT_SETTING "INSERT into autosetting values (null, %d, %d, %d)"
#define QUERY_UPDATE_SETTING "UPDATE autosetting SET hour=%d,min=%d,period=%d WHERE id = 1"
#define QUERY_SELECT_SETTING "SELECT hour,min, period from autosetting WHERE id =1"

#define QUERY_SELECT_COUNT_SETTING "SELECT COUNT(*) FROM autosetting"
#ifdef __cplusplus
}
#endif

