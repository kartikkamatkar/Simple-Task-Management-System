// register.js
document.addEventListener('DOMContentLoaded', function() {
  const form = document.getElementById('registerForm');

  function showError(input, msg) {
    let err = input.parentElement.querySelector('.field-error');
    if (!err) {
      err = document.createElement('div');
      err.className = 'field-error';
      input.parentElement.appendChild(err);
    }
    err.textContent = msg;
  }

  function clearErrors() {
    document.querySelectorAll('.field-error').forEach(e => e.remove());
  }

  form.addEventListener('submit', function(e) {
    e.preventDefault();
    clearErrors();

    const name = form.name.value.trim();
    const email = form.email.value.trim();
    const password = form.password.value.trim();
    const confirm = form.confirmPassword.value.trim();

    let valid = true;
    if (name.length < 2) {
      showError(form.name, "Enter your full name");
      valid = false;
    }
    if (!/\S+@\S+\.\S+/.test(email)) {
      showError(form.email, "Enter a valid email");
      valid = false;
    }
    if (password.length < 6) {
      showError(form.password, "Password must be at least 6 characters");
      valid = false;
    }
    if (password !== confirm) {
      showError(form.confirmPassword, "Passwords do not match");
      valid = false;
    }

    if (!valid) return;

    fetch(form.action || '/register', {
      method: 'POST',
      headers: {'Content-Type':'application/json'},
      body: JSON.stringify({ name, email, password })
    }).then(res => {
      if (res.ok) {
        window.location.href = '/login';
      } else {
        showError(form.confirmPassword, "Signup failed, try again");
      }
    }).catch(() => {
      form.submit();
    });
  });
});
