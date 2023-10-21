import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { Row, Col, Alert } from 'reactstrap';
import { Button } from 'primereact/button'; // Import the PrimeReact Button component

import { useAppSelector } from 'app/config/store';
import { Card } from 'primereact/card';
import { DocumetnIcon } from 'app/shared/layout/header/header-components';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <Row className="text-center align-items-center mx-auto" style={{ height: '420px' }}>
      <Col>
        {/* <h4 className='p-5'> 
          <Translate contentKey="home.title">Welcome, Document Management System!</Translate>
        </h4> */}
      </Col>
      <Col md="9 align-items-center mx-auto">
        {/* <p className="lead">
            <Translate contentKey="home.subtitle">This is your homepage</Translate>
          </p> */}
        {account?.login ? (
          <div>
            <Alert color="success">
              <Translate contentKey="home.logged.message" interpolate={{ username: account.login }}>
                You are logged in as user {account.login}.
              </Translate>
            </Alert>
          </div>
        ) : (
          <div>
            {/* <Col md="3" className="pad">
              <span className="hipster" />
            </Col> */}

            {/* <Translate contentKey="global.messages.info.authenticated.prefix">If you want to </Translate> */}

            {/* 
              <Alert color="warning">
                <Translate contentKey="global.messages.info.register.noaccount">You do not have an account yet?</Translate>&nbsp;
                <Button
                  label="Register a new account"
                  className="alert-link"
                  onClick={() => window.location.href = '/account/register'}
                />
              </Alert> */}
            <Button label="Sign in" className="alert-link  w-50" onClick={() => (window.location.href = '/login')} />
          </div>
        )}
      </Col>
    </Row>
  );
};

export default Home;
