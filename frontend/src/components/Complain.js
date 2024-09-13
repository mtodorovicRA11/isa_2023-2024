import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Complain.css';
import Header from "./Header";

const ComplaintForm = ({ token }) => {
    const [companies, setCompanies] = useState([]);
    const [selectedCompany, setSelectedCompany] = useState('');
    const [complaintText, setComplaintText] = useState('');
    const [responseStatus, setResponseStatus] = useState('');
    const [isAuthenticated, setIsAuthenticated] = useState(false);

    useEffect(() => {
        const checkAuthStatus = () => {
            const token = localStorage.getItem('authToken');
            setIsAuthenticated(!!token);
        };

        checkAuthStatus();
    }, []);
    useEffect(() => {
        axios.get('http://localhost:8080/company/all', {
            headers: { 'Authorization': `Bearer ${token}` }
        }).then(response => setCompanies(response.data))
            .catch(error => console.error('Error fetching companies:', error));
    }, [token]);

    const handleSubmit = () => {
        axios.post('http://localhost:8080/complain/submit', {
            companyId: selectedCompany,
            administratorId: "",
            description: complaintText
        }, {
            headers: { 'Authorization': `Bearer ${token}` }
        }).then(response => {
            setResponseStatus('Complaint submitted successfully');
            setComplaintText('');
        }).catch(error => {
            console.error('Error submitting complaint:', error);
            setResponseStatus('Failed to submit complaint');
        });
    };

    return (
        <div>
            <Header isAuthenticated={isAuthenticated}/>
            <div className="complaint-form-container">
                <h2>Submit a Complaint</h2>
                <select onChange={e => setSelectedCompany(e.target.value)} value={selectedCompany}>
                    <option value="">Select Company</option>
                    {companies.map(company => (
                        <option key={company.id} value={company.id}>{company.name}</option>
                    ))}
                </select>
                <textarea
                    value={complaintText}
                    onChange={e => setComplaintText(e.target.value)}
                    placeholder="Enter your complaint here"
                />
                <button onClick={handleSubmit}>Submit Complaint</button>
                {responseStatus && <p>{responseStatus}</p>}
            </div>
        </div>
    );
};

export default ComplaintForm;
