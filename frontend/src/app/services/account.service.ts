import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Account } from '../model/account';
import { environment } from '../../environments/environment.development';
import { Otp } from '../model/otp';

export const BASE_URL = environment.base_url + '/accounts';
//export const LOAN_URL = environment.base_url + '/loans';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  
  http = inject(HttpClient);
  noauth = { headers: { "noauth": "noauth" } };

  constructor() { }

  createAccount(account: any) {
    return this.http.post(BASE_URL + "/register", account, this.noauth);
  }

  validateOTP(otp: Otp) {
    return this.http.post(BASE_URL + "/OtpValidateAndRegister", otp, this.noauth);
  }

  loginAccount(account: any) {
    return this.http.post(BASE_URL + "/login", account, this.noauth);
  }

  depositBalance(balance: any, password: any) {

    return this.http.patch(BASE_URL + "/deposit/" + balance + "/password/" + password, {});
  }

  withdrawalBalance(balance: any, password: any) {
    return this.http.patch(BASE_URL + "/withdrawal/" + balance + "/password/" + password, {});
  }

  transferBalance(balance: any, reciever: any, password: any) {
    return this.http.patch(BASE_URL + "/transfer/" + reciever + "/balance/" + balance + "/password/" + password, {});
  }

  loanApplication() {
    return this.http.post(BASE_URL + "/loanApplicationEmail", {});
  }

  getCurrentAccount() {
    return this.http.get<Account>(BASE_URL + "/current");
  }

  updateAccount(account: Account) {
    const accountNumber = account.accountNumber;
    return this.http.put<Account>(`${BASE_URL}/${accountNumber}`, account);
  }

  uploadImage(file: File) {
    const formData = new FormData();
    formData.append("file", file);
    return this.http.post<Account>(BASE_URL + "/image", formData);
  }

  generateStatement(startDate: string, endDate: string) {
    return this.http.post(BASE_URL + "/generateStatement/" + startDate + "/date/" + endDate, {});
  }
}
