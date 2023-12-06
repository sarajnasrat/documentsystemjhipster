import './home.scss';

import React from 'react';
import { Row } from 'reactstrap';

import { AUTHORITIES } from 'app/config/constants';
import { useAppSelector } from 'app/config/store';
import { hasAnyAuthority } from 'app/shared/auth/private-route';
import Dashboard from './Dashboard';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);
  const isAdmin = useAppSelector(state => hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.ADMIN]));

  return (
    <Row className="text-center align-items-center mx-auto">
      {isAdmin && (
        <div className="container">
          <Dashboard />
        </div>
      )}
    </Row>
  );
};

export default Home;
