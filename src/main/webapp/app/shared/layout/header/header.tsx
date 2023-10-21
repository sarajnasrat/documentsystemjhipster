import './header.scss';
import { Menubar } from 'primereact/menubar';
import { Dropdown } from 'primereact/dropdown';

import React, { useState, useEffect, useRef } from 'react';
import { Translate, Storage, translate } from 'react-jhipster';
import LoadingBar from 'react-redux-loading-bar';

import { isRTL, languages, locales } from 'app/config/translation';
import { useAppDispatch } from 'app/config/store';
import { setLocale } from 'app/shared/reducers/locale';
import { useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import Form from 'react-bootstrap/Form';
import {
  faArchive,
  faBook,
  faCertificate,
  faCheck,
  faCheckDouble,
  faCogs,
  faEdit,
  faEnvelope,
  faFileArchive,
  faFlag,
  faHeartCircleCheck,
  faLock,
  faReply,
  faScrewdriverWrench,
  faSignOutAlt,
  faSimCard,
  faTableList,
  faTachometerAlt,
  faTowerBroadcast,
  faUser,
  faUserEdit,
  faWrench,
  faPersonArrowUpFromLine,
  faUserCircle,
  faSignInAlt,
} from '@fortawesome/free-solid-svg-icons';
import { Brand, BrandIcon } from './header-components';
import { Menu } from 'primereact/menu';
import { Button } from 'primereact/button';
import { NavDropdown } from 'react-bootstrap';
import { AccountMenu } from '../menus';
export interface IHeaderProps {
  isAuthenticated: boolean;
  isAdmin: boolean;
  ribbonEnv: string;
  isInProduction: boolean;
  isOpenAPIEnabled: boolean;
  currentLocale: string;
}
const Header = (props: IHeaderProps) => {
  const [menuOpen, setMenuOpen] = useState(false);
  const [isAuthenticated, setIsAuthenticated] = useState(props.isAuthenticated);
  const [isAdmin, setIsAdmin] = useState(props.isAdmin);
  const [currentLocale, setCurrentLocale] = useState(props.currentLocale);
  const navigate = useNavigate();
  const menuRef = useRef(null);
  const navigateMenu = path => {
    return navigate(path);
  };
  useEffect(() => document.querySelector('html').setAttribute('dir', isRTL(Storage.session.get('locale')) ? 'rtl' : 'ltr'));
  useEffect(() => {
    setIsAuthenticated(props.isAuthenticated);
    setIsAdmin(props.isAdmin);
    setCurrentLocale(props.currentLocale);
  }, [props]);
  const dispatch = useAppDispatch();

  const handleLocaleChange = event => {
    const langlocale = event.target.value;
    setCurrentLocale(langlocale);
    Storage.session.set('locale', langlocale);
    dispatch(setLocale(langlocale));
    document.querySelector('html').setAttribute('dir', isRTL(langlocale) ? 'rtl' : 'ltr');
  };

  const renderDevRibbon = () =>
    props.isInProduction === false ? (
      <div className="ribbon dev">
        <a href="">
          <Translate contentKey={`global.ribbon.${props.ribbonEnv}`} />
        </a>
      </div>
    ) : null;

  const toggleMenu = () => setMenuOpen(!menuOpen);
  const localeOptions = locales.map(locale => ({
    label: languages[locale].name,
    value: locale,
  }));
  /* jhipster-needle-add-element-to-menu - JHipster will add new menu items here */

  const items = [
    // {
    //   label: translate('global.menu.home'),
    //   // icon: <FontAwesomeIcon icon="home" size="sm" />,
    //   command: () => navigateMenu('/'),
    //   visible: isAuthenticated && isAdmin,
    //   className: 'transparent-font-family color-white',
    // },
    {
      label: translate('global.menu.entities.main'),
      // icon: <FontAwesomeIcon icon={faTableList} size="xs" />,
      className: 'transparent-font-family color-white',
      visible: isAuthenticated && isAdmin,
      items: [
        {
          label: translate('global.menu.entities.documentInfo'),
          // icon: <FontAwesomeIcon icon={faSimCard} size="xs" />,
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
          // icon: <FontAwesomeIcon icon={faUserEdit} size="xs" />,
          command: () => navigateMenu('/admin/user-management'),
          className: 'transparent-font-family color-white',
        },
        {
          label: translate('global.menu.admin.metrics'),
          // icon: <FontAwesomeIcon icon={faTachometerAlt} size="xs" />,
          command: () => navigateMenu('/admin/metrics'),
          className: 'transparent-font-family color-white',
        },
        {
          label: translate('global.menu.admin.health'),
          // icon: <FontAwesomeIcon icon={faHeartCircleCheck} size="xs" />,
          command: () => navigateMenu('/admin/health'),
          className: 'transparent-font-family color-white',
        },
        {
          label: translate('global.menu.admin.configuration'),
          // icon: <FontAwesomeIcon icon={faEdit} size="xs" />,
          command: () => navigateMenu('/admin/configuration'),
          className: 'transparent-font-family color-white',
        },
        {
          label: translate('global.menu.admin.logs'),
          // icon: <FontAwesomeIcon icon={faBook} size="xs" />,
          command: () => navigateMenu('/admin/logs'),
          className: 'transparent-font-family color-white',
        },
        {
          label: translate('global.menu.admin.apidocs'),
          // icon: <FontAwesomeIcon icon={faBook} size="xs" />,
          command: () => navigateMenu('/admin/docs'),
          className: 'transparent-font-family color-white',
        },
      ],
    },
  ];

  const menuItems = [
    {
      label: translate('global.menu.account.settings'),
      icon: <FontAwesomeIcon icon={faWrench} size="xs" />,
      command: () => navigateMenu('/account/settings'),
      className: 'transparent-font-family color-white',
    },
    {
      label: translate('global.menu.account.password'),
      icon: <FontAwesomeIcon icon={faLock} size="xs" />,
      command: () => navigateMenu('/account/password'),
      className: 'transparent-font-family color-white',
    },
    {
      label: translate('global.menu.account.logout'),
      icon: <FontAwesomeIcon icon={faSignOutAlt} size="xs" />,
      command: () => navigateMenu('/logout'),
      className: 'transparent-font-family color-white',
    },
    {
      label: translate('global.menu.account.login'),
      icon: <FontAwesomeIcon icon={faSignInAlt} size="xs" />,
      command: () => navigateMenu('/login'),
      className: 'transparent-font-family color-white',
    },
  ];

  const handleMenuClick = item => {};
  const start = <img alt="logo" src="https://primefaces.org/cdn/primereact/images/logo.png" height="40" className="mr-2"></img>;
  const AccountMenue = () => (
    <div className="d-flex justify-content-space-between">
      <div className="localmenu-wrapper pt-1">
        <Dropdown
          dropdownIcon={
            <i>
              <FontAwesomeIcon icon={faFlag} />
            </i>
          }
          value={currentLocale}
          options={localeOptions}
          onChange={handleLocaleChange}
          placeholder="Select a language"
          className="w-full pos-relative"
        />
      </div>
      {isAuthenticated && (
        <div>
          <i onClick={event => menuRef.current.toggle(event)}>
            <FontAwesomeIcon size="2xl" icon={faUserCircle} />
          </i>
          <Menu model={menuItems} popup ref={menuRef} id="popup_menu_left" popupAlignment="left" />
        </div>
      )}
    </div>
  );

  // const accountMenu = 'some';
  return (
    <div className="p-menuitem">
      {/* {renderDevRibbon()} */}
      {/* <LoadingBar className="loading-bar  d-flex justify-content-between" />
      <Menubar className='bg-black' style={{ height: "50px" }}
        start={Brand}



      /> */}
      <Navbar expand="lg" className="d-flex justify-content-end bg-primary">
        <Container fluid>
          <Navbar.Brand href=""></Navbar.Brand>
          <Navbar.Toggle aria-controls="navbarScroll" />
          <Navbar.Collapse id="navbarScroll" className="flex justify-content-between">
            <Nav className="my-2 my-lg-0" style={{ maxHeight: '100px' }} navbarScroll>
              <div>
                {/* Brand */}
                <Brand />
              </div>
            </Nav>
            <Nav className="my-2 my-lg-0" style={{ maxHeight: '100px', marginRight: 'unset' }} navbarScroll>
              <div>
                <AccountMenue />
              </div>
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>
    </div>
  );
};

export default Header;
