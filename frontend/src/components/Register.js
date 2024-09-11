import React, { useState } from 'react';
import axios from 'axios';
import './Register.css';

const Register = () => {
  // User details state
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [email, setEmail] = useState('');
  const [phoneNumber, setPhoneNumber] = useState('');
  const [jmbg, setJmbg] = useState('');
  const [jobTitle, setJobTitle] = useState('');

  // Form state
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(null);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    setSuccess(null);

    // Validate passwords
    if (password !== confirmPassword) {
      setError('Passwords do not match.');
      setLoading(false);
      return;
    }

    try {
      const user = {
        username: username,
        password: password,
        firstName: firstName,
        lastName: lastName,
        email: email,
        phoneNumber: phoneNumber,
        jmbg: jmbg,
        jobTitle: jobTitle
      };

      await axios.post('http://localhost:8080/auth/register', user);
      setSuccess('Registration successful! Please log in.');
    } catch (error) {
      setError('Registration failed. Please try again.');
      console.error('Registration failed', error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container">
      <div className="form-container">
        <h2>Register</h2>
        <form onSubmit={handleSubmit}>
          <div>
            <label htmlFor="username">Username:</label>
            <input
                id="username"
                type="text"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                required
                className="input-field"
            />
          </div>
          <div>
            <label htmlFor="firstName">First Name:</label>
            <input
                id="firstName"
                type="text"
                value={firstName}
                onChange={(e) => setFirstName(e.target.value)}
                required
                className="input-field"
            />
          </div>
          <div>
            <label htmlFor="lastName">Last name:</label>
            <input
                id="lastName"
                type="text"
                value={lastName}
                onChange={(e) => setLastName(e.target.value)}
                required
                className="input-field"
            />
          </div>
          <div>
            <label htmlFor="email">Email:</label>
            <input
                id="email"
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
                className="input-field"
            />
          </div>
          <div>
            <label htmlFor="password">Password:</label>
            <input
                id="password"
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
                className="input-field"
            />
          </div>
          <div>
            <label htmlFor="confirmPassword">Confirm Password:</label>
            <input
                id="confirmPassword"
                type="password"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                required
                className="input-field"
            />
          </div>
          <div>
            <label htmlFor="phoneNumber">Phone Number:</label>
            <input
                id="phoneNumber"
                type="text"
                value={phoneNumber}
                onChange={(e) => setPhoneNumber(e.target.value)}
                className="input-field"
            />
          </div>
          <div>
            <label htmlFor="jmbg">JMBG:</label>
            <input
                id="jmbg"
                type="text"
                value={jmbg}
                onChange={(e) => setJmbg(e.target.value)}
                className="input-field"
            />
          </div>
          <div>
            <label htmlFor="jobTitle">Job title:</label>
            <input
                id="jobTitle"
                type="text"
                value={jobTitle}
                onChange={(e) => setJobTitle(e.target.value)}
                required
                className="input-field"
            />
          </div>
          <button type="submit" disabled={loading} className="submit-button">
            {loading ? 'Registering...' : 'Register'}
          </button>
        </form>
        {error && <p className="error-message">{error}</p>}
        {success && <p className="success-message">{success}</p>}
      </div>
    </div>
  );
};

export default Register;
