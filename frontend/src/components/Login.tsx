import {GoogleLogin} from '@react-oauth/google';
import {User} from "../types/User";
import http from 'http-common';

interface LoginProps {
  setUser: (user: User) => void
}

export function Login({ setUser }: LoginProps) {
  let nonce = "nonce"
  return (
    <GoogleLogin
      useOneTap
      auto_select
      nonce={nonce}
      onSuccess={
        credentialResponse => {
          console.log(credentialResponse)
          http
            .get(`http://localhost:8080/users/authenticate`, {
              headers: {
                "x-nonce": `${nonce}`,
                Authorization: `Bearer ${credentialResponse.credential}`,
                Accept: 'application/json'
              },
              withCredentials: true
            })
            .then((res) => {
              setUser(res.data)
            })
            .catch((err) => console.log(err));
        }
      }
      onError={() => {
        console.log('Login Failed');
      }}
    />
  )
}
