import {createRoot} from 'react-dom/client';
import {StrictMode} from 'react';
import {GoogleOAuthProvider} from '@react-oauth/google';
import {createBrowserRouter, RouterProvider} from 'react-router-dom'
import {Login} from "./components/Login";
import App from "./App";

const container = document.getElementById('root');
const router = createBrowserRouter([
  {
    path: "/",
    element: <App/>
  }
])

if (container) {
  const root = createRoot(container);
  root.render(
    <StrictMode>
      <GoogleOAuthProvider clientId="1007851537683-bl092bdpmkn89hmiflkm1nf47k39j8vm.apps.googleusercontent.com">
        <RouterProvider router={router}/>
      </GoogleOAuthProvider>
    </StrictMode>
  );
}
