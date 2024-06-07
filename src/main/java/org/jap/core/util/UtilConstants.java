package org.jap.core.util;

public class UtilConstants {
    //La idea es pasar esto a variables de entorno
    //RSA
    public static final String PUBLIC_KEY_RSA = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtSVVJFTFTaw91/B4In0b1kRrMK7D64P82GOjtWf3TBmHh5fTs5fMrFTFeiVOf2voyyNKEeeHVlRaxEdJmiRNgqAU+RqAjkYwfFR+DtPR9idBTI1ljSJE2LIXDW2esKbYPqBsPDgXGSimyceqUJOWTn0v4RvHSW24XhQWUIhd8HXz2AKLKeKo+qAoyTocnXIw/yG/aCZt9xtmf5H1eJZkdSTQMH37jx/go+936I4PGKb7GDZ7iZjK8yGxiIexy9fZlLANM3ksMwC7cY+lSecjZ6C6rjP+TUKUsJ2aAAyH/KskzjCPlh8yEl55bYU9+ZXJN0Z1gd2gb1E6lxMu4GigKQIDAQAB";
    public static final String PRIVATE_KEY_RSA = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC1JVUkVMVNrD3X8HgifRvWRGswrsPrg/zYY6O1Z/dMGYeHl9Ozl8ysVMV6JU5/a+jLI0oR54dWVFrER0maJE2CoBT5GoCORjB8VH4O09H2J0FMjWWNIkTYshcNbZ6wptg+oGw8OBcZKKbJx6pQk5ZOfS/hG8dJbbheFBZQiF3wdfPYAosp4qj6oCjJOhydcjD/Ib9oJm33G2Z/kfV4lmR1JNAwffuPH+Cj73fojg8YpvsYNnuJmMrzIbGIh7HL19mUsA0zeSwzALtxj6VJ5yNnoLquM/5NQpSwnZoADIf8qyTOMI+WHzISXnlthT35lck3RnWB3aBvUTqXEy7gaKApAgMBAAECggEAKNvzYP8IBBWiW3dfubPLmCCVpwT8pz59xVdwQUZipxUpF2IdO+i1H3f9lSgeFSrDdzKODgzR35ymXpiP1wdyPlEaeffK4IyfeiPazUBlHydKmNhZ/CQrME+3WG2N+l2f8DxiPZEPULr1KBVz6rnnkOu25YlNAdiQWhQg1mhz+/1VYB0Y/8OyD34Ut6fcQfQ1O7d5DDSLdEaEthNDz8y2OrOjaHnxUMt1VVCyJaPTQ8OuG8jJQNfr9+lvHaudNIXZBWJbAT2swcYIDBF/49SVhqthhU5vbQ/lMr2/ulFnptwIIv1e976B56el+xLgCMQV9fLxU3VC1wQi9jtL8BuFAwKBgQDvmkTB4gZGh0AH9QWu/0yl4KYAKe+/1dRIpZP48HaBtqJ4gPHsbWDI43WnJMd9IbUIbSq7f+jusAwlqhUaDbfrBaCbCSb5WZvsKvLfzhiPmD1TEwyeA/z1XFedW6wgcGjE309eWi1qhPQFDi8LkayEuwf+gj+r9165DflU0vc8HwKBgQDBiu1Y+jIpnElKhqnB8ys6pQn29bp7ptvimxXykql5lV+Bg8vchDpXVoZzD6UzUknmADU3hzqoLoVgGuidk46FvS4BTcAJYfcl4VrD9DWPXRwjhtLRbFxh0Gh5y+bwKcmICDTu40TrZ/KqSZaMQbkyt3jza3ZB6KkSAujkOD6atwKBgG3giwN2iXYyc/SyMocL4LR5DucoVB/fCVi/Rdp1O4NEo8ErMMZBao71cHDT9v+1Q3W3yk7t234KEaf8FW07OUp37Czsx4i1xUFKeNKVisHfCNBd/f1AyvIiBLPO5GKD6ifOWHFmXUlp6xWeaYiep0S/GG1ogZZ0/GVUuTBjBqn1AoGAWFszNd6qzeJCd1p430lDH/e+T9cotlCbq5z/bQlVg2MVAcyIYfifpcWjhZ3L3l0fzXek09/RgHwUeaZvMZjuhNGz0faLx/zIVqvFwxWW6gco1HM1hVQB7Mm3GHtkx5lcHtUYTULF8O44k1Ma/pb2lFe8SOxzRW2Q2d8oMSAYPIsCgYEAzU5mFMIjMT0XPysw4gYk8tvUIPkji4HkXC10uVNMaTYlQ8XZrHMYnUp/bNI8qrWUqb0zi9ypqHbYvPJAL5AC61cU7me9339GtzWQlG984j4sVessXQ4wSt8hrIs8zfEPiAJ0nUFBzbZkaY2X8Kzw7Dt+rrPvUtoGGeWnO+Et2vk=";
    public static final String RSA_TRANSFORMATION = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
    //JWT
    public static final String SECRET_KEY_JWT = "mySecretKey"; // Store this securely
    public static final long EXPIRATION_TIME_JWT = 86400000;
    //Auth Filter
    public static final String AUTHORIZATION_HEADER = "jwt-token";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String CSRF_HEADER = "X-CSRF-Token";
}
