import React from 'react';
import { Translate } from 'react-jhipster';

import { NavItem, NavLink, NavbarBrand } from 'reactstrap';
import { NavLink as Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { round } from 'lodash';

export const BrandIcon = props => (
  <div {...props} className="brand-icon">
    <img src="content/images/mcit.png" alt="Logo" style={{ height: '40px', width: '30px', padding: '5px' }} />
  </div>
);
export const DocumetnIcon = props => (
  <div {...props} className="brand-icon">
    <img src="content/images/document.jpg" alt="Document" style={{ height: '400px', width: '100%', padding: '5px' }} />
  </div>
);
export const AdminPhoto = props => (
  <div {...props} className="brand-icon">
    <img
      src="content/images/jhipster_family_member_0_head-384.png"
      className="rounded-circle"
      alt="Logo"
      style={{ height: '100px', width: '119px' }}
    />
  </div>
);

export const Brand = () => (
  <NavbarBrand tag={Link} to="/" className="brand-logo">
    <BrandIcon />
    <span className="brand-title">
      <Translate contentKey="global.title">Documentmanagementsytem</Translate>
    </span>
    <span className="navbar-version">{VERSION}</span>
  </NavbarBrand>
);

export const Home = () => (
  <NavItem>
    <NavLink tag={Link} to="/" className="d-flex align-items-center">
      <FontAwesomeIcon icon="home" />
      <span>
        <Translate contentKey="global.menu.home">Home</Translate>
      </span>
    </NavLink>
  </NavItem>
);
export const DocumentInfo = () => (
  <NavItem>
    <NavLink tag={Link} to="/document-info" className="d-flex align-items-center">
      <FontAwesomeIcon icon="book" />
      <span>
        <Translate contentKey="global.menu.entities.documentInfo">DocumentInfo</Translate>
      </span>
    </NavLink>
  </NavItem>
);
export const UserManageMent = () => (
  <NavItem>
    {/* <MenuItem icon="users" to="/admin/user-management">
      <Translate contentKey="global.menu.admin.userManagement">User management</Translate>
    </MenuItem> */}
    <NavLink tag={Link} to="/admin/user-management" className="d-flex align-items-center">
      <FontAwesomeIcon icon="users" />
      <span>
        <Translate contentKey="global.menu.admin.userManagement">User</Translate>
      </span>
    </NavLink>
  </NavItem>
);
