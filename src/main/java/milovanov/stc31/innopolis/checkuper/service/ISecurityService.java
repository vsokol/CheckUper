package milovanov.stc31.innopolis.checkuper.service;

import milovanov.stc31.innopolis.checkuper.pojo.ResultCheck;

public interface ISecurityService {
    void autoLogin(String username, String password);
    void reLogin();
    ResultCheck checkPassword(String newPassword, String passwordConfirm);
}
