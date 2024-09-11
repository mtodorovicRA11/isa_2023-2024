import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Profile.css';

const Profile = ({ token }) => {
    const [reservedTimeslots, setReservedTimeslots] = useState([]);
    const [cancelStatus, setCancelStatus] = useState('');

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
        axios.post(`http://localhost:8080/reservation/cancel/${reservationId}`, {
            headers: {
                'Authorization': `Bearer ${token}`
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

    return (
        <div className="profile-container">
            <header className="profile-header">
                <h1>User Profile</h1>
            </header>
            <main className="profile-content">
                <h2>Reserved Timeslots</h2>
                {reservedTimeslots.length > 0 ? (
                    <ul className="reserved-timeslot-list">
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
                            </li>
                        ))}
                    </ul>
                ) : (
                    <p>No reservations found.</p>
                )}
                {cancelStatus && <p className="cancel-status">{cancelStatus}</p>}
            </main>
            <footer className="profile-footer">
                <p>&copy; 2024 Medical Equipment Reservations</p>
            </footer>
        </div>
    );
};

export default Profile;
