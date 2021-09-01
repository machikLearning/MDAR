class NotFoundCrawlingException(Exception):
    def __str__(self):
        return "Not Found"

class ConnectError(Exception):
    def __str__(self):
        return "Maximum try"