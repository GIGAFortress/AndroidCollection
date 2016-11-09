package gigafortress.simplemvp.biz;

import gigafortress.simplemvp.bean.User;

/**
 * 业务类接口
 * 接口不能被继承，类可以被继承
 * 在UserBiz中被调用， 在UserLoginPresenter中被具体实现
 */
public interface OnLoginListener {
    void loginSuccess(User user);

    void loginFailed();
}
