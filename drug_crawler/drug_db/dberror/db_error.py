class DBSQLError(Exception):
    def __str__(self):
        return "SQL ERROR"