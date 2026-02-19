package edu.ezip.ing1.pds.connectionPoolV3;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBConfigV3 {


    private String driver;
    private String url;

    private String password;

    private String login;

    private int poolSize;




    public String getDriver()   { return driver;   }
    public String getUrl()      { return url;       }
    public String getLogin()     { return login;      }
    public String getPassword() { return password;  }
    public int    getPoolSize() { return poolSize;  }



    public DBConfigV3() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("db.properties")) {
            props.load(fis);
            this.driver   = props.getProperty("driver").trim();
            this.url      = props.getProperty("url").trim();
            this.login      = props.getProperty("login").trim();
            this.poolSize = Integer.parseInt(props.getProperty("poolSize").trim());
            this.password = props.getProperty("password").trim();

        } catch (IOException e) {
            throw new RuntimeException("Impossible de lire db.properties : " + e.getMessage(), e);
        }
    }



}
