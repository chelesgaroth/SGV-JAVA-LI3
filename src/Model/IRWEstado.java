package Model;

import java.io.*;

public interface IRWEstado {
    void setFileOut(String file1);
    void setFileIn(String file2);
    void saveData(IGestaoVendas sgv) throws IOException;
    IGestaoVendas loadData() throws IOException, ClassNotFoundException;
}
