import 'app/config/dayjs.ts';
import React, { useEffect } from 'react';
import { Storage, translate } from 'react-jhipster';
import { BrowserRouter } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { Card, Col, Row } from 'reactstrap';
import './app.scss';

import { faHome, faUsers } from '@fortawesome/free-solid-svg-icons';
import { faBook } from '@fortawesome/free-solid-svg-icons/faBook';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { AUTHORITIES } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import AppRoutes from 'app/routes';
import { hasAnyAuthority } from 'app/shared/auth/private-route';
import ErrorBoundary from 'app/shared/error/error-boundary';
import { getProfile } from 'app/shared/reducers/application-profile';
import { getSession } from 'app/shared/reducers/authentication';
import { setLocale } from 'app/shared/reducers/locale';
import { Button } from 'primereact/button';
import { PanelMenu } from 'primereact/panelmenu';
import { useRef, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { isRTL } from './config/translation';
import Header from './shared/layout/header/header';
import { AdminPhoto } from './shared/layout/header/header-components';

const baseHref = document.querySelector('base').getAttribute('href').replace(/\/$/, '');
export interface IHeaderProps {
  isAuthenticated: boolean;
  isAdmin: boolean;
  ribbonEnv: string;
  isInProduction: boolean;
  isOpenAPIEnabled: boolean;
  currentLocale: string;
}
export const App = () => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getSession());
    dispatch(getProfile());
  }, []);

  const currentLocale = useAppSelector(state => state.locale.currentLocale);
  const isAuthenticated = useAppSelector(state => state.authentication.isAuthenticated);
  const isAdmin = useAppSelector(state => hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.ADMIN]));
  const ribbonEnv = useAppSelector(state => state.applicationProfile.ribbonEnv);
  const isInProduction = useAppSelector(state => state.applicationProfile.inProduction);
  const isOpenAPIEnabled = useAppSelector(state => state.applicationProfile.isOpenAPIEnabled);

  const paddingTop = '60px';
  const items = [
    {
      key: '/document-info',
      label: 'DocumentInfo',
    },
    {
      key: '/account/register',
      label: 'Register',
    },
    {
      key: '/usermanagement',
      label: 'Employee Experience',
    },
    {
      key: '/',
      label: 'Employee Experience',
    },
  ];
  const customIcon = (
    <>
      <div
        className="d-flex mx-auto mx-auto rounded-circle mt-2"
        style={{
          backgroundColor: 'gray',
          width: '120px',
          height: '100px',
          border: '1px solid white',
          borderRadius: '30px',
          alignItems: 'center',
        }}
      >
        <div>
          <AdminPhoto />
        </div>
      </div>
      <div className="mx-auto">
        <h5 className="mx-auto text-center pt-2">Admin</h5>
      </div>
    </>
  );
  const [menuOpen, setMenuOpen] = useState(false);

  useEffect(() => document.querySelector('html').setAttribute('dir', isRTL(Storage.session.get('locale')) ? 'rtl' : 'ltr'));

  const handleLocaleChange = event => {
    const langKey = event.target.value;
    Storage.session.set('locale', langKey);
    dispatch(setLocale(langKey));
    document.querySelector('html').setAttribute('dir', isRTL(langKey) ? 'rtl' : 'ltr');
  };

  const [show, setShow] = useState(true);
  const toggleVisible = () => {
    setShow(!show);
  };
  const SideBar = props => {
    const headerComponent = <Header {...props} />;

    const navigate = useNavigate();
    const menuRef = useRef(null);
    const navigateMenu = path => {
      return navigate(path);
    };
    const itemsss = [
      {
        label: translate('global.menu.home'),
        icon: <FontAwesomeIcon icon={faHome} size="xs" />,
        command: () => navigateMenu('/home'),
        className: 'transparent-font-family color-white',
      },
      {
        label: translate('global.menu.entities.documentInfo'),
        icon: <FontAwesomeIcon icon={faBook} size="xs" />,
        command: () => navigateMenu('/document-info'),
        className: 'transparent-font-family color-white',
      },
      {
        label: translate('global.menu.admin.userManagement'),
        icon: <FontAwesomeIcon icon={faUsers} size="xs" />,
        command: () => navigateMenu('/admin/user-management'),
        className: 'transparent-font-family color-white',
      },
    ];

    return (
      <div className="sidebar" style={{ listStyleType: 'none', width: '200px', padding: '15px' }}>
        <div>{isAdmin && <PanelMenu model={itemsss} className="w-full md:w-25rem" />}</div>
      </div>
    );
  };
  return (
    <BrowserRouter basename={baseHref}>
      <div className="app-container" dir={currentLocale === 'fa' || currentLocale === 'pa' ? 'rtl' : 'ltr'}>
        <ToastContainer position={toast.POSITION.TOP_LEFT} className="toastify-container" toastClassName="toastify-toast" />
        {isAdmin && (
          <div>
            <ErrorBoundary>
              <Header
                isAuthenticated={isAuthenticated}
                isAdmin={isAdmin}
                currentLocale={currentLocale}
                ribbonEnv={ribbonEnv}
                isInProduction={isInProduction}
                isOpenAPIEnabled={isOpenAPIEnabled}
              />
            </ErrorBoundary>
          </div>
        )}
        <Row mb="4">
          {show ? (
            <>
              <SideBar />
              <Col sm="12" md="10">
                <div className="d-flex justify-content-space-between">
                  <div className="container-fluid view-container" id="app-view-container">
                    <ErrorBoundary>
                      <AppRoutes />
                    </ErrorBoundary>
                  </div>
                </div>
              </Col>
            </>
          ) : (
            <Col sm="12" md="12" className="d-flex">
              <div>
                <Button
                  id="sidebar-show"
                  className="no-outline align-items-baseline"
                  style={{ backgroundColor: 'GrayText', padding: 'none', borderRadius: 'none' }}
                  icon="pi pi-list"
                  onClick={toggleVisible}
                ></Button>
              </div>

              <div className="container-fluid view-container" id="app-view-container">
                <Card className="jh-card ">
                  <ErrorBoundary>
                    <AppRoutes />
                  </ErrorBoundary>
                </Card>
                {/* <Footer /> */}
              </div>
            </Col>
          )}
        </Row>
      </div>
    </BrowserRouter>
  );
};

export default App;
