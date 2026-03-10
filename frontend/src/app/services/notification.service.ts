import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { NotificationModalComponent, NotificationData } from '../components/notification-modal';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  constructor(private dialog: MatDialog) {}

  success(title: string, message: string): void {
    this.dialog.open(NotificationModalComponent, {
      width: '400px',
      maxWidth: '90vw',
      disableClose: false,
      position: { top: '50px' },
      data: {
        title,
        message,
        type: 'success'
      } as NotificationData
    });
  }

  error(title: string, message: string): void {
    this.dialog.open(NotificationModalComponent, {
      width: '400px',
      maxWidth: '90vw',
      disableClose: false,
      position: { top: '50px' },
      data: {
        title,
        message,
        type: 'error'
      } as NotificationData
    });
  }

  warning(title: string, message: string): void {
    this.dialog.open(NotificationModalComponent, {
      width: '400px',
      maxWidth: '90vw',
      disableClose: false,
      position: { top: '50px' },
      data: {
        title,
        message,
        type: 'warning'
      } as NotificationData
    });
  }

  info(title: string, message: string): void {
    this.dialog.open(NotificationModalComponent, {
      width: '400px',
      maxWidth: '90vw',
      disableClose: false,
      position: { top: '50px' },
      data: {
        title,
        message,
        type: 'info'
      } as NotificationData
    });
  }
}
