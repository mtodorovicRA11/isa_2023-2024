package com.ftn.isa.service;

import com.ftn.isa.model.Reservation;
import com.ftn.isa.model.User;

public interface QRCodeService {
    String generateQRCode(User user, Reservation reservation);
    byte[] getQRCodeImage(String text, int width, int height);
}
