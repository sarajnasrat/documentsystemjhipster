import 'react-toastify/dist/ReactToastify.css';
import './app.scss';
import 'app/config/dayjs.ts';
import { Translate, Storage, translate } from 'react-jhipster';
import React, { useEffect } from 'react';
import { Card, Collapse, Nav, Navbar, NavbarToggler } from 'reactstrap';
import { BrowserRouter, NavLink } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getSession } from 'app/shared/reducers/authentication';
import { getProfile } from 'app/shared/reducers/application-profile';
import { setLocale } from 'app/shared/reducers/locale';
import Footer from 'app/shared/layout/footer/footer';
import { hasAnyAuthority } from 'app/shared/auth/private-route';
import ErrorBoundary from 'app/shared/error/error-boundary';
import { AUTHORITIES } from 'app/config/constants';
import AppRoutes from 'app/routes';
import { AccountMenu, AdminMenu, LocaleMenu, EntitiesMenu } from './shared/layout/menus';
import Header from './shared/layout/header/header';
import EntitiesMenuItems from 'app/entities/menu';
import { isRTL } from './config/translation';
import { useState,useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import { AdminPhoto, DocumentInfo, Home, UserManageMent } from './shared/layout/header/header-components';
import { Menubar } from 'primereact/menubar';
import { MegaMenu } from 'primereact/megamenu';
import { PanelMenu } from 'primereact/panelmenu';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEdit, faHeartCircleCheck, faTableList, faTachometerAlt, faUser, faUserEdit, faUsers } from '@fortawesome/free-solid-svg-icons';
import { faBook } from '@fortawesome/free-solid-svg-icons/faBook';


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
      label: "DocumentInfo",
    },
    {
      key: '/account/register',
      label: "Register"
    },
    {
      key: '/usermanagement',
      label: "Employee Experience"
    },
    {
      key: '/',
      label: "Employee Experience"
    },
  ];
  const customIcon = (

    <>
      <div className='d-flex mx-auto mx-auto rounded-circle mt-2' style={{ backgroundColor: "gray", width: "120px", height: "100px", border: "1px solid white", borderRadius: "30px", alignItems: "center" }}>
        <div>
          <AdminPhoto />
        </div>

      </div>
      <div className='mx-auto'>
        <h5 className='mx-auto text-center pt-2'>
          Admin
        </h5>

      </div>
    </>
  )
  const [menuOpen, setMenuOpen] = useState(false);

  useEffect(() => document.querySelector('html').setAttribute('dir', isRTL(Storage.session.get('locale')) ? 'rtl' : 'ltr'));

  const handleLocaleChange = event => {
    const langKey = event.target.value;
    Storage.session.set('locale', langKey);
    dispatch(setLocale(langKey));
    document.querySelector('html').setAttribute('dir', isRTL(langKey) ? 'rtl' : 'ltr');
  };


  const SideBar = (props) => {
    const headerComponent = <Header {...props} />;
    const navigate = useNavigate();
    const menuRef = useRef(null);
    const navigateMenu = path => {
      return navigate(path);
    };
    const itemss = [
      {
          label:'File',
          icon:'pi pi-fw pi-file',
          items:[
              {
                  label:'New',
                  icon:'pi pi-fw pi-plus',
                  items:[
                  {
                      label:'Bookmark',
                      icon:'pi pi-fw pi-bookmark'
                  },
                  {
                      label:'Video',
                      icon:'pi pi-fw pi-video'
                  }
                  ]
              },
              {
                  label:'Delete',
                  icon:'pi pi-fw pi-trash'
              },
              {
                  label:'Export',
                  icon:'pi pi-fw pi-external-link'
              }
          ]
      },
      {
          label:'Edit',
          icon:'pi pi-fw pi-pencil',
          items:[
              {
                  label:'Left',
                  icon:'pi pi-fw pi-align-left'
              },
              {
                  label:'Right',
                  icon:'pi pi-fw pi-align-right'
              },
              {
                  label:'Center',
                  icon:'pi pi-fw pi-align-center'
              },
              {
                  label:'Justify',
                  icon:'pi pi-fw pi-align-justify'
              }
          ]
      },
      {
          label:'Users',
          icon:'pi pi-fw pi-user',
          items:[
              {
                  label:'New',
                  icon:'pi pi-fw pi-user-plus'
              },
              {
                  label:'Delete',
                  icon:'pi pi-fw pi-user-minus'
              },
              {
                  label:'Search',
                  icon:'pi pi-fw pi-users',
                  items:[
                  {
                      label:'Filter',
                      icon:'pi pi-fw pi-filter',
                      items:[
                          {
                              label:'Print',
                              icon:'pi pi-fw pi-print'
                          }
                      ]
                  },
                  {
                      icon:'pi pi-fw pi-bars',
                      label:'List'
                  }
                  ]
              }
          ]
      },
      {
          label:'Events',
          icon:'pi pi-fw pi-calendar',
          items:[
              {
                  label:'Edit',
                  icon:'pi pi-fw pi-pencil',
                  items:[
                  {
                      label:'Save',
                      icon:'pi pi-fw pi-calendar-plus'
                  },
                  {
                      label:'Delete',
                      icon:'pi pi-fw pi-calendar-minus'
                  }
                  ]
              },
              {
                  label:'Archive',
                  icon:'pi pi-fw pi-calendar-times',
                  items:[
                  {
                      label:'Remove',
                      icon:'pi pi-fw pi-calendar-minus'
                  }
                  ]
              }
          ]
      }
  ];

    
    
    const itemsss = [
      // {
      //   label: translate('global.menu.home'),
      //   // icon: <FontAwesomeIcon icon="home" size="sm" />,
      //   command: () => navigateMenu('/'),
      //   visible: isAuthenticated && isAdmin,
      //   className: 'transparent-font-family color-white',
      // },
      {
        label: translate('global.menu.entities.main'),
        icon: <FontAwesomeIcon icon={faTableList} size="xs" />,
        className: 'transparent-font-family color-white',
        visible: isAuthenticated && isAdmin,
        items: [
          {
            label: translate('global.menu.entities.documentInfo'),
            icon: <FontAwesomeIcon icon={faBook} size="xs" />,
            command: () => navigateMenu('/document-info'),
            className: 'transparent-font-family color-white',
          },
        ],
      },
      {
        label: translate('global.menu.admin.main'),
        // icon: <FontAwesomeIcon icon={faScrewdriverWrench} size="xs" />,
        className: 'transparent-font-family color-white',
        visible: isAuthenticated && isAdmin,
  
        items: [
          {
            label: translate('global.menu.admin.userManagement'),
            icon: <FontAwesomeIcon icon={faUsers} size="xs" />,
            command: () => navigateMenu('/admin/user-management'),
            className: 'transparent-font-family color-white',
          },
          {
            label: translate('global.menu.admin.metrics'),
            icon: <FontAwesomeIcon icon={faTachometerAlt} size="xs" />,
            command: () => navigateMenu('/admin/metrics'),
            className: 'transparent-font-family color-white',
          },
          {
            label: translate('global.menu.admin.health'),
            icon: <FontAwesomeIcon icon={faHeartCircleCheck} size="xs" />,
            command: () => navigateMenu('/admin/health'),
            className: 'transparent-font-family color-white',
          },
          {
            label: translate('global.menu.admin.configuration'),
            icon: <FontAwesomeIcon icon={faEdit} size="xs" />,
            command: () => navigateMenu('/admin/configuration'),
            className: 'transparent-font-family color-white',
          },
          {
            label: translate('global.menu.admin.logs'),
            icon: <FontAwesomeIcon icon={faBook} size="xs" />,
            command: () => navigateMenu('/admin/logs'),
            className: 'transparent-font-family color-white',
          },
          {
            label: translate('global.menu.admin.apidocs'),
            icon: <FontAwesomeIcon icon={faBook} size="xs" />,
            command: () => navigateMenu('/admin/docs'),
            className: 'transparent-font-family color-white',
          },
        ],
      },
    ];
    return (
      <div className="sidebar" style={{ listStyleType: "none", width: "230px", padding: "15px" }}>
        <div >
          {customIcon}
          <div className='sidbar-item'>
          <Home />
        </div>
          <PanelMenu model={itemsss} className="w-full md:w-25rem" />
        </div>
        {/* <Header
        isAuthenticated={isAuthenticated}
        isAdmin={isAdmin}
        currentLocale={currentLocale}
        ribbonEnv={ribbonEnv}
        isInProduction={isInProduction}
        isOpenAPIEnabled={isOpenAPIEnabled}
      /> */}
        {/* <div className='sideMenu'>
        <div className="flex justify-content-center sidebarposition">
          {items.map((item, index) => (
            <NavLink to={item.key} key={index} className="navigationlink">
              <div className="link-text">{item.label}</div>
            </NavLink>
          ))}
        </div>
      </div> */}
     
        {/* <div className='sidbar-item'>
          <DocumentInfo />
        </div>
        <div className='sidbar-item'>
          <UserManageMent />
        </div> */}
        {/* <EntitiesMenu />

        <AdminMenu showOpenAPI={props.isOpenAPIEnabled} />
        <LocaleMenu currentLocale={props.currentLocale} onClick={handleLocaleChange} />
        <AccountMenu isAuthenticated={props.isAuthenticated} />
 */}

      </div>
    )

  };
  return (
    <BrowserRouter basename={baseHref}>
      <div className="app-container" >
        <ToastContainer position={toast.POSITION.TOP_LEFT} className="toastify-container" toastClassName="toastify-toast" />
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
        <div className='d-flex justify-content-space-between'>
          {isAdmin && <SideBar />}
          <div className="container-fluid view-container" id="app-view-container">
            <Card className="jh-card">
              <ErrorBoundary>
                <AppRoutes />
              </ErrorBoundary>
            </Card>
            {/* <Footer /> */}
          </div>
        </div>
      </div>
    </BrowserRouter>
  );
};

export default App;
