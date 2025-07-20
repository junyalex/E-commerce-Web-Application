// If an error occurs, alert it
document.addEventListener('DOMContentLoaded', function () {
    const serverErrorMessage = document.documentElement.dataset.errorMessage;
    if (serverErrorMessage) {
        alert(serverErrorMessage);
    }
});

// Select elements for validation check
const fieldsToValidate = [
    { input: document.getElementById('email'), error: document.getElementById('emailError'), validator: validateEmail },
    { input: document.getElementById('name'), error: document.getElementById('nameError'), validator: validateNotEmpty },
    { input: document.getElementById('emailConfirm'), error: document.getElementById('emailConfirmError'), validator: validateEmailConfirm },
    { input: document.getElementById('password'), error: document.getElementById('passwordError'), validator: validatePassword },
    { input: document.getElementById('passwordConfirm'), error: document.getElementById('passwordConfirmError'), validator: validatePasswordConfirm },
    { input: document.getElementById('address'), error: document.getElementById('addressError'), validator: validateNotEmpty }
];

// Add eventListener to each element
fieldsToValidate.forEach(field => {
    if (field.input) {
        field.input.addEventListener('input', () => {
            validateField(field.input, field.error, field.validator);
        });
    }
});

// Validation Test when user tries to submit
const signupForm = document.getElementById('signupForm');
if (signupForm) {
    signupForm.addEventListener('submit', function (event) {
        let isFormValid = true;
        fieldsToValidate.forEach(field => {
            if (!validateField(field.input, field.error, field.validator)) {
                isFormValid = false;
            }
        });

        // if user's input is incomplete, prevent users from submitting
        if (!isFormValid) {
            event.preventDefault();
            fieldsToValidate.forEach(field => {
                validateField(field.input, field.error, field.validator);
            })
        }
    });
}

function validateField(inputEl, errorEl, validatorFn) {
    if (!inputEl || !errorEl) return true;

    const isValid = validatorFn(inputEl.value);

    if (!isValid) {
        inputEl.classList.add('invalid');
        errorEl.style.display = 'block';
    } else {
        inputEl.classList.remove('invalid');
        errorEl.style.display = 'none';
    }
    return isValid;
}

function validateNotEmpty(value) {
    return value.trim() !== '';
}

function validateEmail(value) {
    if (value.trim() === '') return false;
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(value);
}

function validateEmailConfirm(value) {
    if (value.trim() === '') return false;
    const email = document.getElementById('email').value;
    return value === email;
}

function validatePassword(value) {
    const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,16}$/;
    return passwordRegex.test(value);
}

function validatePasswordConfirm(value) {
    if (value.trim() === '') return false;
    const password = document.getElementById('password').value;
    return value === password;
}

// Button that allows users to toggle on / off password
function togglePassword(id, toggleBtn) {
    const input = document.getElementById(id);
    const icon = toggleBtn.querySelector('i');

    if (!input || !icon) return;

    if (input.type === 'password') {
        input.type = 'text';
        icon.className = 'fa fa-eye-slash';
    } else {
        input.type = 'password';
        icon.className = 'fa fa-eye';
    }
}