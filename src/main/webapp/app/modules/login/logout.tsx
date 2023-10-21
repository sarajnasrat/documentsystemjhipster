import React, { useLayoutEffect } from 'react';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { logout } from 'app/shared/reducers/authentication';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';

export const Logout = () => {
  const logoutUrl = useAppSelector(state => state.authentication.logoutUrl);
  console.log('Logout' + logoutUrl);
  const dispatch = useAppDispatch();

  useLayoutEffect(() => {
    dispatch(logout());
    if (logoutUrl) {
      window.location.href = logoutUrl;
    }
  });

  return (
    <div className="p-5">
      <h4>Logged out successfully!</h4>

      <Link to="/login" className="alert-link">
        <Translate contentKey="global.messages.info.authenticated.link"> sign in</Translate>
      </Link>
    </div>
  );
};

export default Logout;
