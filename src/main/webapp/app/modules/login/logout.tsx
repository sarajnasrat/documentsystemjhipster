import { useAppDispatch, useAppSelector } from 'app/config/store';
import { logout } from 'app/shared/reducers/authentication';
import React, { useLayoutEffect } from 'react';

import { Navigate } from 'react-router-dom';

export const Logout = () => {
  const logoutUrl = useAppSelector(state => state.authentication.logoutUrl);
  const dispatch = useAppDispatch();
  useLayoutEffect(() => {
    dispatch(logout());
    if (logoutUrl) {
      window.location.href = logoutUrl;
    }
  });

  // Use the Redirect component to navigate to the login page
  return <Navigate to="/" />;
};

export default Logout;
