const validateName = (name) => {
    // 이름은 최소 2자 이상, 특수문자와 숫자는 포함되지 않도록 설정
    const nameRegex = /^[a-zA-Z가-힣 ]{2,}$/;

    if (nameRegex.test(name)) {
        return true;
    } else {
        return false;
    }
};

export default { validateName };
