import React, {useState, useEffect} from 'react';
import axios from 'axios';
import './Profile.css';
import Header from "./Header";

const Profile = ({token}) => {
    const [reservedTimeslots, setReservedTimeslots] = useState([]);
    const [cancelStatus, setCancelStatus] = useState('');
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const [userData, setUserData] = useState({
        username: '',
        firstname: '',
        lastname: '',
        email: '',
        phoneNumber: '',
        jmbg: '',
        jobTitle: '',
    });

    useEffect(() => {
        const checkAuthStatus = () => {
            const token = localStorage.getItem('authToken');
            setIsAuthenticated(!!token);
        };

        checkAuthStatus();
    }, []);

    useEffect(() => {
        axios.get('http://localhost:8080/equipment/reservations', {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })
            .then(response => {
                setReservedTimeslots(response.data);
            })
            .catch(error => {
                console.error('Error fetching reserved timeslots:', error);
            });
    }, [token]);

    const handleCancelReservation = (reservationId) => {
        console.log(token)
        axios.post(`http://localhost:8080/reservation/cancel`, null, {
            headers: {
                'Authorization': `Bearer ${token}`
            },
            params: {
                reservationId: reservationId
            }
        })
            .then(() => {
                setReservedTimeslots(reservedTimeslots.filter(reservation => reservation.id !== reservationId));
                setCancelStatus('Reservation canceled successfully');
            })
            .catch(error => {
                console.error('Error canceling reservation:', error);
                setCancelStatus('Failed to cancel reservation');
            });
    };


    useEffect(() => {
        axios.get('http://localhost:8080/user/whoami', { // Assuming this is the endpoint for fetching user profile
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })
            .then(response => {
                setUserData(response.data);
            })
            .catch(error => {
                console.error('Error fetching user profile:', error);
            });
    }, [token]);

    return (<div><Header isAuthenticated={isAuthenticated}/>
        <div className="profile-container">
            <header className="profile-header">
                <h1>User Profile</h1>
            </header>
            <ul className="profile-details">
                <li><strong>Username:</strong> {userData.username}</li>
                <li><strong>First Name:</strong> {userData.firstname}</li>
                <li><strong>Last Name:</strong> {userData.lastname}</li>
                <li><strong>Email:</strong> {userData.email}</li>
                <li><strong>Phone Number:</strong> {userData.phoneNumber}</li>
                <li><strong>JMBG:</strong> {userData.jmbg}</li>
                <li><strong>Job Title:</strong> {userData.jobTitle}</li>
            </ul>
            <main className="profile-content">
                <h2>Reserved Timeslots</h2>
                {reservedTimeslots.length > 0 ? (<ul className="reserved-timeslot-list">
                    {reservedTimeslots.map(reservation => (
                        <li key={reservation.id} className="reserved-timeslot-item">
                            <div>
                                <strong>{reservation.timeSlot.equipment.name}</strong>
                            </div>
                            <div>
                                {reservation.timeSlot.startTime} - {reservation.timeSlot.endTime}
                            </div>
                            <button
                                className="cancel-button"
                                onClick={() => handleCancelReservation(reservation.id)}
                            >
                                Cancel
                            </button>
                        </li>))}
                </ul>) : (<p>No reservations found.</p>)}
                {cancelStatus && <p className="cancel-status">{cancelStatus}</p>}
            </main>
            <footer className="profile-footer">
                <p>&copy; 2024 Medical Equipment Reservations</p>
            </footer>
        </div>
    </div>);
};

export default Profile;
