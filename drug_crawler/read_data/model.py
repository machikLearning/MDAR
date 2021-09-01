from abc import *

class AbsReadData(metaclass=ABCMeta):

    @abstractmethod
    def read_data(self):
        pass
