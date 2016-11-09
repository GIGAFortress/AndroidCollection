package gigafortress.simplemvp.biz;

/**
 * UserBiz实现该接口，UserLoginPresenter实例化并使用该接口
 */
public interface IUserBiz {
    public void login(String username, String password, OnLoginListener loginListener);
}
