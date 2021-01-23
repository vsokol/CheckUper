package milovanov.stc31.innopolis.checkuper.service;

public interface ISecurityService {
    void autoLogin(String username, String password);
    void reLogin();
}
