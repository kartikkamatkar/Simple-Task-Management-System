// login.js
document.addEventListener('DOMContentLoaded', function() {
  const form = document.getElementById('loginForm');

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

    const email = form.email.value.trim();
    const password = form.password.value.trim();

    let valid = true;
    if (!/\S+@\S+\.\S+/.test(email)) {
      showError(form.email, "Enter a valid email");
      valid = false;
    }
    if (password.length < 6) {
      showError(form.password, "Password must be at least 6 characters");
      valid = false;
    }

    if (!valid) return;

    // Example AJAX submit
    fetch(form.action || '/login', {
      method: 'POST',
      headers: {'Content-Type':'application/json'},
      body: JSON.stringify({ email, password })
    }).then(res => {
      if (res.ok) {
        window.location.href = '/dashboard';
      } else {
        showError(form.password, "Invalid credentials");
      }
    }).catch(() => {
      form.submit(); // fallback
    });
  });
});
