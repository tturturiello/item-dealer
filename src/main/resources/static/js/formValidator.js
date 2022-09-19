(function() {
    class Field {
        constructor(element) {
            this.element = element;
            this.isValid = false;
        }
    }

    const firstName = new Field(document.getElementById( 'firstname' ));
    const lastName = new Field(document.getElementById( 'lastname' ));
    const userName = new Field(document.getElementById( 'username' ));
    const password = new Field(document.getElementById( 'password' ));
    const passwordConf = new Field(document.getElementById( 'password_confirm' ));

    const fieldList = [ firstName, lastName, userName, password, passwordConf ];
    const submit = document.getElementById('register_submit')
    submit.classList.add("disabled");

    checkFieldEvent(firstName, () => !firstName.element.value.search( /^[a-zA-Z]+$/g ));
    checkFieldEvent(lastName, () => !lastName.element.value.search( /^[a-zA-Z]+$/g ));
    checkFieldEvent(userName, () => !userName.element.value.search( /^[a-zA-Z\d_]+$/g ));
    checkFieldEvent(password, () => !password.element.value.search( /^[a-zA-Z\d_]+$/g )
        && password.element.value.length >= 8
        && password.element.value.length <= 15
    );
    checkFieldEvent(passwordConf, () => !password.element.value.search( /^[a-zA-Z\d_]+$/g )
        && passwordConf.element.value === password.element.value
    );

    function checkFieldEvent(field, condition) {
        field.element.addEventListener('keyup', () => {
            field.isValid = condition();
            if(field.isValid) {
                field.element.classList.remove("is-invalid");
                field.element.classList.add("is-valid");
            } else {
                field.element.classList.remove("is-valid");
                field.element.classList.add("is-invalid");
            }
            if (isValidSubmit()) {
                submit.classList.remove("disabled");
            } else {
                submit.classList.add("disabled");
            }
        });
    }

    function isValidSubmit() {
        return [...fieldList]
            .map(e => e.isValid)
            .reduce((e, acc) => e && acc);
    }
})();